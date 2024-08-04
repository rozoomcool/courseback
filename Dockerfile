#FROM gradle:4.5-jdk8-alpine as builder
#USER root
#WORKDIR /builder
#ADD . /builder
#RUN gradle build --stacktrace
#
#
#FROM openjdk:8-jre-alpine
#WORKDIR /app
#EXPOSE 8080
#COPY --from=builder /builder/build/libs/courses-0.0.1-SNAPSHOT.jar .
#ENTRYPOINT ["sh", "-c", "java", "-jar", "server.jar"]

# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Создаем директорию для приложения
WORKDIR /app

# Копируем jar файл в контейнер
COPY build/libs/courses-0.0.1-SNAPSHOT.jar app.jar

# Устанавливаем переменные окружения для Java
ENV JAVA_OPTS="-Xms512m -Xmx2g"

# Открываем порт для приложения
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
