FROM gradle:8.7.0-jdk21

LABEL authors="sova"

#WORKDIR /app

#COPY /app .

RUN gradle installDist

CMD ./build/install/app/bin/app
