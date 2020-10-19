FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/spring_security.jar spring_security.jar
ENTRYPOINT ["java","-jar","/spring_security.jar", "&"]