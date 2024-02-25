# Multi-stage Optimized Dockerfile
# the first stage of our build will build and extract the layers
FROM maven:3.6.3-adoptopenjdk-11 as builder

LABEL maintainer="Michael Abah"
WORKDIR /application
COPY ./pom.xml ./
# store maven dependencies so next build doesn't have to download them again
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean package -DskipTests
# LAYERED JAR
ARG JAR_FILE=target/*.jar
RUN cp ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# the second stage of our build will copy the extracted layers
FROM openjdk:11-jre-slim as runtime
LABEL maintainer="Michael Abah"
RUN apt-get update \
	&& apt-get install -y --no-install-recommends curl \
	&& rm -rf /var/lib/apt/lists/*
RUN useradd -d /home/gotrah -m -s /bin/bash abah
USER abah
WORKDIR /application
COPY --from=builder /application/dependencies/ ./
RUN true
COPY --from=builder /application/spring-boot-loader/ ./
RUN true
COPY --from=builder /application/snapshot-dependencies/ ./
RUN true
COPY --from=builder /application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
HEALTHCHECK --start-period=10s CMD curl -f http://localhost:9091/health || exit 1
