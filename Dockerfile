FROM openjdk:8-alpine

WORKDIR /usr/src/app

COPY /build/libs/app.jar /usr/src/app/app.jar

CMD ["java", "-jar", "./app.jar"]
