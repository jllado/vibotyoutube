FROM adoptopenjdk/openjdk12:x86_64-ubuntu-jre-12.0.2_10
LABEL maintainer="jllado@gmail.com"
EXPOSE 8080
ADD build/libs/vibotyoutube-0.0.1-SNAPSHOT.jar app.jar
ADD .youtube_client_secret.json .youtube_client_secret.json
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]