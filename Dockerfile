FROM openjdk:8-jdk-alpine
WORKDIR /usr/app
COPY ./target/nibss-easy-pay-api-0.0.1-SNAPSHOT.jar ./
CMD java -jar nibss-easy-pay-api-0.0.1-SNAPSHOT.jar