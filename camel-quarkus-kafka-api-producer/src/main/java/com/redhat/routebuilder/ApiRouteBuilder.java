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
        protected String KAFKA_PRODUCER_COMPRESSION_CODEC = "{{quarkus.openshift.env.vars.kafka-producer-compression-codec}}";
        protected String KAFKA_PRODUCER_REQUIRED_ACKS = "{{quarkus.openshift.env.vars.kafka-producer-required-acks}}";
        protected String KAFKA_PRODUCER_BUFFER_MEMORY_SIZE = "{{quarkus.openshift.env.vars.kafka-producer-buffer-memory-size}}";
        protected String KAFKA_PRODUCER_LINGER_MS = "{{quarkus.openshift.env.vars.kafka-producer-linger-ms}}";
        protected String KAFKA_PRODUCER_BATCH_SIZE = "{{quarkus.openshift.env.vars.kafka-producer-batch-size}}";
        protected String KAFKA_PRODUCER_SECURITY_PROTOCOL = "{{quarkus.openshift.env.vars.kafka-producer-security-protocol}}";
        protected String KAFKA_PRODUCER_TRUSTSTORE_LOCATION = "{{quarkus.openshift.env.vars.kafka-producer-ssl-truststore-location}}";
        protected String KAFKA_PRODUCER_TRUSTSTORE_PASSWORD = "{{quarkus.openshift.env.vars.kafka-producer-ssl-truststore-password}}";
        protected String KAFKA_PRODUCER_SASL_MECHANISM = "{{quarkus.openshift.env.vars.kafka-producer-sasl-mechanism}}";
        protected String KAFKA_PRODUCER_JAAS_CONFIG = "{{quarkus.openshift.env.vars.kafka-producer-sasl-jaas-config}}";


        @Override
        public void configure() throws Exception {
                // sets Opentelemetry
                OpenTelemetryTracer ott = new OpenTelemetryTracer();
                ott.setCamelContext(this.getCamelContext());
                ott.init();
                System.out.println("O classpath é: " + System.getProperty("java.class.path"));

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
                                // .responseMessage().code(200).message("Dados enviados com sucesso para o
                                // tópico Kafka").endResponseMessage()
                                .routeId("postProductSend")
                                .to("direct:sendToKafka");
                // .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                // .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))

                // Route that sends message to kafka topic
                from("direct:sendToKafka").routeId("sendToKafka")
                               //  .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
                                .log(LoggingLevel.INFO, "request " + "${body}")
                                .to("kafka:" + KAFKA_TOPIC + "?brokers=" + KAFKA_BOOTSTRAP_SERVERS +
                                                "&compressionCodec=" + KAFKA_PRODUCER_COMPRESSION_CODEC +
                                                "&requestRequiredAcks=" + KAFKA_PRODUCER_REQUIRED_ACKS +
                                                "&bufferMemorySize=" + KAFKA_PRODUCER_BUFFER_MEMORY_SIZE +
                                                "&lingerMs=" + KAFKA_PRODUCER_LINGER_MS +
                                                "&producerBatchSize=" + KAFKA_PRODUCER_BATCH_SIZE +
                                                "&securityProtocol=" + KAFKA_PRODUCER_SECURITY_PROTOCOL +
                                                "&sslTruststoreLocation=" + KAFKA_PRODUCER_TRUSTSTORE_LOCATION +
                                                "&sslTruststorePassword=" + KAFKA_PRODUCER_TRUSTSTORE_PASSWORD +                                               
                                                "&saslMechanism=" + KAFKA_PRODUCER_SASL_MECHANISM +
                                                "&saslJaasConfig=" + KAFKA_PRODUCER_JAAS_CONFIG +
                                                "");
        }
}