FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/guli_parent.jar demojenkins.jar
ENTRYPOINT ["java","-jar","/guli_parent.jar", "&"]