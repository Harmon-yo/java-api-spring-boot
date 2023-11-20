FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar","/harmonyo-spring-api-0.0.1-SNAPSHOT-exec.jar"]