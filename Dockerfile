FROM gradle:8.7.0-jdk21

LABEL authors="sova"

WORKDIR /app

COPY / .

RUN ./gradlew installDist

EXPOSE 8080

CMD ./build/install/app/bin/app --spring.profiles.active=prod
