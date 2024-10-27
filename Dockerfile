FROM gradle:8.7.0-jdk21

LABEL authors="sova"

WORKDIR /app

COPY / .

RUN ./gradlew installDist

CMD ./build/install/app/bin/app
