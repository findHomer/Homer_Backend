FROM openjdk:8-alpine

WORKDIR /usr/src/app

COPY /build/libs/app.jar /usr/src/app/app.jar

RUN mkdir -p /usr/src/app/dump

CMD ["java", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/usr/src/app/dump", "-jar", "./app.jar"]
