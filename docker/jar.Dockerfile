# we will use openjdk 8 with alpine as it is a very small linux distro
FROM  openjdk:11-jdk
ARG JAR_FILE=target/app.jar
# copy the packaged jar file into our docker image
COPY ${JAR_FILE} /app.jar

# environment variable with default value
ENV SPRING_PROFILE=
ENV PORT 8080
EXPOSE $PORT

# set the startup command to execute the jar
CMD [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILE} -jar /app.jar" ]