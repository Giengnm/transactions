FROM test-mvn-openshift

USER root

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn clean install

ENV PORT 8080
EXPOSE $PORT
CMD [ "sh", "-c", "mvn -Dserver.port=${PORT} spring-boot:run -Drun.arguments=--PORT=$PORT" ]
