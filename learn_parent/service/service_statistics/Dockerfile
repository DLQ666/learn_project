FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_statistics-0.0.1-SNAPSHOT.jar service_statistics-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/service_statistics-0.0.1-SNAPSHOT.jar", "&"]
