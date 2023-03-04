FROM amazoncorretto:17-alpine
MAINTAINER paradaux.io

WORKDIR /app
COPY . /app
VOLUME /tmp

ENTRYPOINT ["./gradlew", "run"]