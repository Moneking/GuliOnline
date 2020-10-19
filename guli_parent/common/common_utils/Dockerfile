FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/common_utils.jar common_utils.jar
ENTRYPOINT ["java","-jar","/common_utils.jar", "&"]