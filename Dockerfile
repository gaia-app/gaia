FROM maven:3-jdk-14 as BUILD

COPY . /usr/src/app
RUN mvn --batch-mode -DskipTests -f /usr/src/app/pom.xml clean package

FROM openjdk:14-jdk
EXPOSE 8080
COPY --from=BUILD /usr/src/app/target/*.jar /opt/target/gaia.jar
WORKDIR /opt/target

CMD ["java", "-jar", "gaia.jar"]
