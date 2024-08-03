FROM gradle:8.9.0-jdk17-focal as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace


FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY --from=builder /builder/build/libs/courses-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["sh", "-c", "java", "-jar", "server.jar"]