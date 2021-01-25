FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_acl-0.0.1-SNAPSHOT.jar service_acl-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/service_acl-0.0.1-SNAPSHOT.jar", "&"]
