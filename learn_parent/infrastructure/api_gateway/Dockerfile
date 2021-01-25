FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/api_gateway-0.0.1-SNAPSHOT.jar api_gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/api_gateway-0.0.1-SNAPSHOT.jar", "&"]
