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
    protected String KAFKA_GROUP_ID = "{{quarkus.openshift.env.vars.kafka-group-id}}";

    @Override
    public void configure() throws Exception {
        // sets Opentelemetry
        OpenTelemetryTracer ott = new OpenTelemetryTracer();
        ott.init(this.getContext());
        // Route that consumes message to kafka topic
        from("kafka:" + KAFKA_TOPIC + "?brokers=" + KAFKA_BOOTSTRAP_SERVERS + "&groupId=" + KAFKA_GROUP_ID +
                "&securityProtocol=SASL_SSL" +
                "&sslTruststoreLocation=/etc/security/truststore/truststore.jks" +
                "&sslTruststorePassword=redhat" +
                "&saslMechanism=SCRAM-SHA-512" +
                "&saslJaasConfig=org.apache.kafka.common.security.scram.ScramLoginModule required username=\"redhat-user\" password=\"redhat123\";")
                .routeId("consume")
                .log(LoggingLevel.INFO, "message: " + "${body}");
    }

}