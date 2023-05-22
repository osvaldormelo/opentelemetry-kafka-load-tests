package com.redhat.routeBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.LoggingLevel;
import org.apache.camel.opentelemetry.OpenTelemetryTracer;

public class ApiRouteBuilder extends RouteBuilder {
    protected String KAFKA_TOPIC = "{{quarkus.openshift.env.vars.kafka-topic}}";
    protected String KAFKA_BOOTSTRAP_SERVERS = "{{quarkus.openshift.env.vars.kafka-bootstrap-servers}}";

    @Override
    public void configure() throws Exception {
        //init Opentelemetry
        OpenTelemetryTracer ott = new OpenTelemetryTracer();
        ott.init(this.getContext());
        // REST and Open API configuration        
        restConfiguration().bindingMode(RestBindingMode.auto)
                .component("platform-http")
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/").port(8080)
                .apiContextPath("/openapi")
                .apiProperty("api.title", "Camel Quarkus Kafka API Producer Demo")
                .apiProperty("api.version", "1.0.0-SNAPSHOT")
                .apiProperty("cors", "true");

        // REST methods configuration
        rest().tag("API Demo using Camel and Quarkus")
                .produces("text/plain")
                .post("/produce")
                .consumes("text/plain")
                .description("Send product to kafka")
                .route().routeId("postProductSend")
                .to("direct:sendToKafka")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .endRest();
        // Route that sends message to kafka topic
        from("direct:sendToKafka").routeId("sendToKafka")                
                //.setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
                .log("request " + "${body}")
                .to("kafka:" + KAFKA_TOPIC + "?brokers=" + KAFKA_BOOTSTRAP_SERVERS);
    }
}
