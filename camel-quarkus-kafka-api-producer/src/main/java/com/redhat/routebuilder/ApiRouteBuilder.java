package com.redhat.routeBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.LoggingLevel;
import org.apache.camel.opentelemetry.OpenTelemetryTracer;

import io.opentelemetry.api.trace.Tracer;


public class ApiRouteBuilder extends RouteBuilder {
        protected String KAFKA_TOPIC = "{{quarkus.openshift.env.vars.kafka-topic}}";
        protected String KAFKA_BOOTSTRAP_SERVERS = "{{quarkus.openshift.env.vars.kafka-bootstrap-servers}}";
        
        @Override
        public void configure() throws Exception {
                // sets Opentelemetry
                OpenTelemetryTracer ott = new OpenTelemetryTracer();        
                ott.setCamelContext(this.getCamelContext());
                ott.init();               

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
                rest("/produce").tag("API Demo using Camel and Quarkus")
                                .produces("text/plain")
                                .post()
                                .consumes("text/plain")
                                .description("Send product to kafka")
                                //.responseMessage().code(200).message("Dados enviados com sucesso para o tópico Kafka").endResponseMessage()
                                
                                .routeId("postProductSend")
                                .to("direct:sendToKafka");
                                //.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                                //.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                        
                // Route that sends message to kafka topic
                from("direct:sendToKafka").routeId("sendToKafka")
                                // .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
                                .log(LoggingLevel.INFO, "request " + "${body}")
                                .to("kafka:" + KAFKA_TOPIC + "?brokers=" + KAFKA_BOOTSTRAP_SERVERS + 
                              //  "&securityProtocol=SSL" +
                              //  "&sslTruststoreLocation=classpath:/truststore.jks" +
                              //  "&sslTruststorePassword=redhat" +
                                "");
        }
}