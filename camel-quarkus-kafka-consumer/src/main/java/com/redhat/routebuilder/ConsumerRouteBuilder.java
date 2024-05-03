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
    protected String KAFKA_CONSUMER_MAX_POLL_RECORDS = "{{quarkus.openshift.env.vars.kafka-consumer-max-poll-records}}";
    protected String KAFKA_CONSUMER_AUTO_COMMIT_ENABLE = "{{quarkus.openshift.env.vars.kafka-consumer-auto-commit-enable}}";
    protected String KAFKA_CONSUMER_FETCH_MIN_BYTES = "{{quarkus.openshift.env.vars.kafka-consumer-fetch-min-bytes}}";
    protected String KAFKA_CONSUMER_FETCH_WAIT_MAX_MS = "{{quarkus.openshift.env.vars.kafka-consumer-fetch-wait-max-ms}}";
    protected String KAFKA_CONSUMER_FETCH_MAX_BYTES = "{{quarkus.openshift.env.vars.kafka-consumer-fetch-max-bytes}}"; 
    protected String KAFKA_CONSUMER_SECURITY_PROTOCOL = "{{quarkus.openshift.env.vars.kafka-consumer-security-protocol}}";
    protected String KAFKA_CONSUMER_TRUSTSTORE_LOCATION = "{{quarkus.openshift.env.vars.kafka-consumer-ssl-truststore-location}}";
    protected String KAFKA_CONSUMER_TRUSTSTORE_PASSWORD = "{{quarkus.openshift.env.vars.kafka-consumer-ssl-truststore-password}}";
    protected String KAFKA_CONSUMER_SASL_MECHANISM = "{{quarkus.openshift.env.vars.kafka-consumer-sasl-mechanism}}";
    protected String KAFKA_CONSUMER_JAAS_CONFIG = "{{quarkus.openshift.env.vars.kafka-consumer-sasl-jaas-config}}";

    @Override
    public void configure() throws Exception {
        // sets Opentelemetry
        OpenTelemetryTracer ott = new OpenTelemetryTracer();
        ott.init(this.getContext());
        // Route that consumes message to kafka topic
        from("kafka:" + KAFKA_TOPIC + "?brokers=" + KAFKA_BOOTSTRAP_SERVERS + "&groupId=" + KAFKA_GROUP_ID +
                "&maxPollRecords=" + KAFKA_CONSUMER_MAX_POLL_RECORDS +
                "&autoCommitEnable=" + KAFKA_CONSUMER_AUTO_COMMIT_ENABLE +
                "&fetchMinBytes=" + KAFKA_CONSUMER_FETCH_MIN_BYTES +
                "&fetchWaitMaxMs=" + KAFKA_CONSUMER_FETCH_WAIT_MAX_MS +
                "&fetchMaxBytes=" + KAFKA_CONSUMER_FETCH_MAX_BYTES +
                "&securityProtocol=" + KAFKA_CONSUMER_SECURITY_PROTOCOL +
                "&sslTruststoreLocation=" + KAFKA_CONSUMER_TRUSTSTORE_LOCATION +
                "&sslTruststorePassword=" + KAFKA_CONSUMER_TRUSTSTORE_PASSWORD +                                               
                "&saslMechanism=" + KAFKA_CONSUMER_SASL_MECHANISM +
                "&saslJaasConfig=" + KAFKA_CONSUMER_JAAS_CONFIG )
                .routeId("consume")
                .log(LoggingLevel.INFO, "message: " + "${body}");
    }

}