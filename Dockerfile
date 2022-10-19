FROM openjdk:8-jdk-alpine
WORKDIR /usr/app
COPY ./target/accessbankimto-0.0.1-SNAPSHOT.jar ./
CMD java -jar accessbankimto-0.0.1-SNAPSHOT.jar