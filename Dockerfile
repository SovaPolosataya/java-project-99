FROM gradle:8.7.0-jdk21

LABEL authors="sova"

WORKDIR /

COPY / .

RUN gradlew build

CMD ./build/install/app/bin/app
