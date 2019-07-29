FROM openjdk:8-jdk-alpine
WORKDIR /root
COPY target/DoodleBoards-0.0.1.jar .
CMD java -jar DoodleBoards-0.0.1.jar
s