// camel-k: dependency=mvn:io.quarkus:quarkus-opentelemetry-exporter-otlp
// camel-k: dependency=mvn:io.opentelemetry:opentelemetry-exporter-logging
// camel-k: dependency=mvn:io.quarkus:quarkus-opentelemetry
// camel-k: dependency=mvn:org.apache.camel.quarkus:camel-quarkus-opentelemetry
// camel-k: dependency=mvn:com.auth0:java-jwt:4.4.0

package com.redhat.routeBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.LoggingLevel;
import org.apache.camel.opentelemetry.OpenTelemetryTracer;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

// import io.opentelemetry.api.trace.Tracer;

// mosquitto_pub -h localhost -t eclipse-mosquitto -m "olá povo" --property Authorization 123456

public class ApiRouteBuilder extends RouteBuilder {
    protected String KAFKA_TOPIC = "{{quarkus.openshift.env.vars.kafka-topic}}";
    protected String KAFKA_BOOTSTRAP_SERVERS = "kafka-cluster-kafka-bootstrap.kafka.svc.cluster.local:9092";

    @Override
    public void configure() throws Exception {
        // sets Opentelemetry
        OpenTelemetryTracer ott = new OpenTelemetryTracer();
        ott.setCamelContext(this.getCamelContext());
        ott.init();

        //from("paho-mqtt5?host=tcp://myMqttBroker1:1883")
        from("paho-mqtt5:eclipse-mosquitto?brokerUrl=tcp://eclipse-mosquitto:1883")
                .log("Mensagem recebida: ${body}")
                .log("Header: ${headers}")
                .process(exchange -> {
                    System.out.println("Mensagem recebida: " + exchange.getIn().getBody());
                    System.out.println("Header: " + exchange.getIn().getHeaders());
                    System.out.println("Authorization: " + exchange.getIn().getHeader("Authorization"));
                    
                    String token = exchange.getIn().getHeader("Authorization", String.class);
                    // implements jwt logic.
                    String role = decodeJwt(token);
                    // Define role header for routing.
                    exchange.getIn().setHeader("userRole", role);
                })
                .choice()
                    .when(header("userRole").isEqualTo("role1"))
                        .log("Enviando para o tópico topicRole1")
                        .to("kafka:topicRole1?brokers=" + KAFKA_BOOTSTRAP_SERVERS)
                    .when(header("userRole").isEqualTo("role2"))
                        .log("Enviando para o tópico topicRole2")
                        .to("kafka:topicRole2?brokers=" + KAFKA_BOOTSTRAP_SERVERS)
                    .otherwise()
                        .log("Enviando para o tópico default")
                        .to("kafka:topicDefault?brokers=" + KAFKA_BOOTSTRAP_SERVERS)
                .endChoice();


        // Route that sends message to kafka topic
        from("direct:sendToKafka").routeId("sendToKafka")
                // .setHeader(KafkaConstants.KEY, constant("Camel")) // Key of the message
                .log(LoggingLevel.INFO, "request " + "${body}")
                .to("kafka:teste?brokers=" + KAFKA_BOOTSTRAP_SERVERS);
    }

    public String decodeJwt(String token) {
        try {

            Algorithm algorithm = Algorithm.HMAC256("ourSecret");
            JWTVerifier verifier = JWT.require(algorithm).build();

            // Verificar e decodificar o token
            DecodedJWT jwt = verifier.verify(token);

            // Extrair a role da claim; ajuste isso conforme necessário
            String role = jwt.getClaim("role").asString();
            return role;
        } catch (Exception exception) {
            // Em caso de falha na decodificação, logue ou trate o erro conforme necessário
            System.err.println("Erro ao decodificar o JWT: " + exception.getMessage());
            return null; // ou trate como achar melhor
        }
    }
}