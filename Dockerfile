FROM openjdk:14-alpine
COPY build/libs/multiple-kafka-streams-in-micronaut-demo-*-all.jar multiple-kafka-streams-in-micronaut-demo.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "multiple-kafka-streams-in-micronaut-demo.jar"]