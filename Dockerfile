FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD
#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD FOR JAVA 8

WORKDIR /build/

#ARG SPRING_ACTIVE_PROFILE

COPY pom.xml .
COPY src src

RUN mvn clean install -DskipTests -Dspring.profiles.active=dev&& mvn package -DskipTests -B -e -Dspring.profiles.active=dev
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#RUN mvn clean install -Dspring.profiles.active=dev && mvn package -B -e -Dspring.profiles.active=dev

#RUN mvn clean && mvn package 

FROM openjdk:11-slim
#FROM openjdk:8-alpine FOR JAVA 8
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/profile-service-api.jar
ENTRYPOINT ["java", "-jar", "profile-service-api.jar"]

