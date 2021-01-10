
# Quarkus example

The initial example was created with the command:

```bash
mvn io.quarkus:quarkus-maven-plugin:0.11.0:create -DprojectGroupId=com.systelab -DprojectArtifactId=quarkus-example -DclassName="com.systelab.patient.controller.PatientController" -Dpath="/patient"
```

## Getting Started

To get you started you can simply clone the `quarkus-example` repository and install the dependencies.

### Prerequisites

You need [git][git] to clone the `quarkus-example` repository.

You will need [Java™ SE Development Kit 8][jdk-download] and [Maven][maven].

### Clone

Clone the `quarkus-example` repository using git:

```bash
git clone https://github.com/alfonsserra/quarkus-example.git
cd quarkus-example
```

### Install Dependencies

In order to install the dependencies and generate the jar you must run:

```bash
mvn package
```

Generate a native application with [GraalVM][graal], with the following command:

```bash
mvn package -Pnative
```

### Development mode

To run in development mode, run the following command:

```bash
mvn clean compile quarkus:dev
```

### Run

To launch the server, simply run with java -jar the generated jar file.

```bash
cd target
java -jar quarkus-example-1.0-SNAPSHOT-runner.jar
```

or run the native image:

```bash
cd target
./quarkus-example-1.0-SNAPSHOT-runner
```

Swagger UI is accessible at http://localhost:8080/swagger-ui

### Docker

Run the following commands to generate a Docker image:

```bash
mvn package -Pnative -Dnative-image.docker-build=true
docker build -f src/main/docker/Dockerfile -t quarkus/quarkus-example .
```

### Command line

Create a patient:

```bash
curl -d '{"name":"John", "surname":"Avila"}' -H "Content-Type: application/json" -X POST http://localhost:8080/patients/patient
```

Get the patient list

```bash
curl  -H "Content-Type: application/json" http://localhost:8080/patients
```




[git]: https://git-scm.com/
[sboot]: https://projects.spring.io/spring-boot/
[maven]: https://maven.apache.org/download.cgi
[jdk-download]: http://www.oracle.com/technetwork/java/javase/downloads
[JEE]: http://www.oracle.com/technetwork/java/javaee/tech/index.html
[jwt]: https://jwt.io/
[cors]: https://en.wikipedia.org/wiki/Cross-origin_resource_sharing
[swagger]: https://swagger.io/
[allure]: https://docs.qameta.io/allure/
[junit]: https://junit.org/junit5/
[graal]: https://www.graalvm.org


