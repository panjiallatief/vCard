FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/vcard.jar 
COPY src/main/resources/application.yml /app/application.yml
WORKDIR /app
CMD ["java","-jar","vcard.jar"]