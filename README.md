# Automated Testing of callerid API

This article outlines how to run the following tests for the callerid API:

- unit tests
- integration tests
- performance tests

## Getting Started

These instructions outline how to launch callerid automation tests on your local machine.

### Prerequisites

- Tarball from [here](https://drive.google.com/open?id=1BihyLmPDvxhGWxWzo5ccGdIYq3DjZUvQ)
- Unoccupied proxy port (e.g. 9090)
- Gradle
- Gatling
- Maven
- Springboot

## Running the tests

Start with a clean build and wait for Spring Boot to load:

```
./start_app.sh
```

To run unit tests:
   
```
gradle unitTest
```

To run integration tests:

```
gradle integrationTest
```

To run performance tests:

```
gradle loadTest
```

NOTE: Performance test will default to proxy port 9090. To specify a different port running Spring Boot:

```
gradle loadTest -PserverPort=(port number)
```

### Run end to end test

Run unit, integration, and performance tests together:

```
gradle unitTest integrationTest loadTest
```

### Test results

Obtain detailed results for the following tests:

Unit Tests:
```
./build/reports/tests/unitTest/packages/mypackage.testunit.html
```

Integration Tests:
```
./build/reports/tests/unitTest/packages/mypackage.testintegration.html
```

Performance Tests:
```
./build/gatling-results/webservicecallsimulation-(..)/index.html
```

## Deployment

See Dockerfile [here](./Dockerfile)

## Built With

* [JUnit](https://junit.org/junit4/) - The testing framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Gatling](https://gatling.io) - Used for performance testing

## Authors

* **Maciej Kocol** - *Initial work*
