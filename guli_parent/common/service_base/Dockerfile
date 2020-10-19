FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/service_base.jar service_base.jar
ENTRYPOINT ["java","-jar","/service_base.jar", "&"]