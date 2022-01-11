# Enrichment

## Table of Contents

1. [General Info](#general-info)
2. [Reference Documentation](#reference-documentation)

   a. [Technologies](#technologies)

3. [Banner Info](#banner-info)
4. [Database Info](#database-info)
5. [Logs Info](#logs-info)
6. [Code Coverage](#code-coverage)
7. [Run it](#run-it)

   a.[Run with Maven in local](#run-with-maven-in-local)

   b.[Run docker](#run-docker)

8. [Health Info](#health-info)
9. [Swagger Info](#swagger-info)

## General Info

* The original package name is 'net.bank.transaction'.

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.1/maven-plugin/reference/html/#build-image)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.4.1/reference/htmlsingle/#production-ready)
* [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc)

### Technologies

A list of technologies used within the project:

* Java: openjdk 11
* Spring Boot: version 2.6.2
* Spring Webflux: version (boot-version)
* Spring Actuator: version (boot-version)
* Spring Data R2DBC: version (boot-version)
    * with h2-r2dbc: version 0.8.4.RELEASE
* Mappers (maStruct): version 1.4.2-final
* Slf4j:
    * logback: version 1.2.10
    * logstash: version 7.0.1
    * logback jackson: version 0.1.5
* Open API UI (webflux): version 1.5.2
* Test with:
    * Junit 5
    * Mockito
    * cucumber: version 7.2.1

## Banner Info

* Banner generate in banner/banner.txt
* Application properties:

```yaml
spring:
  banner:
    location: classpath:/banner/banner.txt
```

For include the scheme, we use bean in TransactionApplication.java

```java
    ...

@Bean
public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory){

    ConnectionFactoryInitializer initializer=new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);

    CompositeDatabasePopulator populator=new CompositeDatabasePopulator();
    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource(
    "database/schema.sql")));
    initializer.setDatabasePopulator(populator);

    return initializer;
    }
```

## Database Info

* We are using [r2dbc](https://spring.io/projects/spring-data-r2dbc) with h2

```yaml
spring:
  ...
  r2dbc:
    url: r2dbc:h2:mem:///transactions?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    name: sa
    password: password
```

## Logs Info

This project uses the logback and logstash for logs:

* The logback configuration: resource/logback.xml
    - Console appender
    ```xml
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    ```
    - File appender: with json formatter generate in ./logs/log-(date).log
    ```xml
    <appender name="STDFILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${LOG_DIR}/log-${byDay}.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
            <FileNamePattern>@{LOG_FILE}.json.log.%i</FileNamePattern>
            <maxIndex>10</maxIndex>
        </rollingPolicy>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>20MB</MaxFileSize>
        </triggeringPolicy>
    </appender>
    ```
* Logging level in application.yaml

```yaml
logging:
  level:
    root: INFO
    com.cesce: DEBUG
    org.springframework.boot: INFO
    org.springframework.data.r2dbc: DEBUG
```

## Code Coverage

This project uses the sonar and postgresql for code coverage:

As we do not have sonar in local environment, we have to install it, we recommend using docker to
generate the necessary services. You must run the sonar-postgres.yml
file ([Docker Info](#docker-info)):

```shell
$ docker-compose -f sonar-postgres.yml up -d
```

for up the code coverage, you must compile the project:

```shell
$ mvn clean install 
```

```shell
$ mvn verify
```

After compile the project, you run the sonar plugin:

```shell
$ mvn sonar:sonar 
```

Now, go to the url **[http://localhost:9000](http://localhost:9000)**, and show the project code
coverage

[Configure settings](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/)

The pom configuration with jacoco, surefire and sonar plugin:

```xml

<build>
  <plugins>
    ...

    <plugin>
      <groupId>org.sonarsource.scanner.maven</groupId>
      <artifactId>sonar-maven-plugin</artifactId>
      <version>${sonar-maven-plugin.version}</version>
    </plugin>

    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>${maven-surefire-plugin.version}</version>
      <configuration>
        <useSystemClassLoader>false</useSystemClassLoader>
        <!-- Sets the VM argument line used when unit tests are run. -->
        <!-- Excludes integration tests when unit tests are run. -->
        <excludes>
          <exclude>**/*Cucumber*.java</exclude>
          <exclude>**/*IT.java</exclude>
        </excludes>
      </configuration>
    </plugin>

    <!-- Test de integración/cucumber -->

    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-failsafe-plugin</artifactId>
      <version>${maven-failsafe-plugin.version}</version>
      <executions>
        <execution>
          <goals>
            <goal>integration-test</goal>
          </goals>
          <configuration>
            <includes>
              <include>**/*IT.class</include>
            </includes>
          </configuration>
        </execution>
      </executions>
    </plugin>

    <!-- JaCoCo Cobertura -->
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>${jacoco-maven-plugin.version}</version>
      <executions>
        <execution>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <!-- attached to Maven test phase -->
        <execution>
          <id>report</id>
          <phase>verify</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

The properties of plugins:

```xml

<properties>
  ...
  <!-- JACOCO / SONAR plugins -->
  <jacoco-maven-plugin.version>0.8.7</jacoco-maven-plugin.version>
  <maven-surefire-plugin.version>2.22.2</maven-surefire-plugin.version>
  <maven-failsafe-plugin.version>2.22.2</maven-failsafe-plugin.version>
  <sonar-maven-plugin.version>3.9.1.2184</sonar-maven-plugin.version>
  <!-- sonar -->
  <sonar.sources>${project.basedir}/src/main/</sonar.sources>
  <sonar.tests>${project.basedir}/src/test/</sonar.tests>
  <sonar.java.codeCoveragePlugin>jacoco</sonar.java.codeCoveragePlugin>
  <sonar.jacoco.reportPaths>
    ${project.build.directory}/coverage-reports/jacoco-ut.exec,
    ${project.build.directory}/coverage-reports/jacoco-it.exec,
    ${project.build.directory}/coverage-reports/jacoco-at.exec
  </sonar.jacoco.reportPaths>
  <sonar.junit.reportPaths>
    ${project.build.directory}/surefire-reports
    ${project.build.directory}/failsafe-reports
  </sonar.junit.reportPaths>
  <sonar.exclusions>**/*Application.java</sonar.exclusions>
</properties>
```

## Run it

### Run with maven in local

The code repository is:

* clone the repository:

```shell
$ git clone https://github.com/Giengnm/transactions.git
```

* move to project repository:

```shell
$ cd ../path/transactions
```

* generate application:

```shell
$ mvn clean install
```

* run the app in local:

``` shell
   $ mvn spring-boot:run
```

or you alsa execute like a java

``` shell
   $ java -jar target/app.jar net.bank.transactions.TransactionsApplication
```

Call to: http://localhost:8080

### Run docker

#### Docker Info

There is a docker folder in the root project, where we can save the Dockerfiles necessary for the
project.

```shell
$ cd ../path/transactions/docker
```

#### maven image

You must run the Dockerfile file ([Docker Info](#docker-info)):

* clone the repository:

```shell
$ git clone https://github.com/Giengnm/transactions.git
```

First you must build the image(do not forget change versions if is necessary):

```shell
$ docker build -t transactions:v1 -f docker/maven.Dockerfile .
```

Second, you must generate de docker container with image(do not forget publish the ports):

```shell
$ docker run -p 8080:8080 transactions:v1
```

Also, without logs

```shell
$ docker run -d -p 8080:8080 transactions:v1
```

Call to: http://localhost:8080

Show docker images:

```shell
$ docker images
```

#### jdk image

You must run the Dockerfile file ([Docker Info](#docker-info)):

* clone the repository:

```shell
$ git clone https://github.com/Giengnm/transactions.git
```

First you must build the image(do not forget change versions if is necessary):

```shell
$ docker build -t transactions:v1 -f docker/jar.Dockerfile .
```

Second, you must generate de docker container with image(do not forget publish the ports):

```shell
$ docker run -p 8080:8080 transactions:v1
```

Also, without logs

```shell
$ docker run -d -p 8080:8080 -t transactions:v1
```

Call to: http://localhost:8080

Show docker images:

```shell
$ docker images
```

## Health Info

* In local url health: http://localhost:8080/actuator/health

## Swagger Info

* In local url swagger: http://localhost:8080/swagger-ui-html