FROM openjdk:11
EXPOSE 8086
ARG JAR_FILE=/moderatorservice/target/moderatorservice-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]