.DEFAULT_GOAL := build-run

run-dist:
	./build/install/app/bin/app

build:
	./gradlew clean build

clean:
	./gradlew clean

start:
	./gradlew bootRun --args='--spring.profiles.active=dev'

start-prod:
	./gradlew bootRun --args='--spring.profiles.active=prod'

install:
	./gradlew installDist

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

.PHONY: build
