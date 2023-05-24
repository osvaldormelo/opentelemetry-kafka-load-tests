package com.redhat.routeBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.LoggingLevel;
import org.apache.camel.opentelemetry.OpenTelemetryTracer;

public class ConsumerRouteBuilder extends RouteBuilder {
    protected String KAFKA_TOPIC = "{{quarkus.openshift.env.vars.kafka-topic}}";
    protected String KAFKA_BOOTSTRAP_SERVERS = "{{quarkus.openshift.env.vars.kafka-bootstrap-servers}}";

    @Override
    public void configure() throws Exception {
        //sets Opentelemetry
        OpenTelemetryTracer ott = new OpenTelemetryTracer();
        ott.init(this.getContext());
        // Route that consumes message to kafka topic
        from("kafka:" + KAFKA_TOPIC + "?brokers=" + KAFKA_BOOTSTRAP_SERVERS).routeId("consume")             
               
                .log(LoggingLevel.INFO, "message: " + "${body}");
    }     

}