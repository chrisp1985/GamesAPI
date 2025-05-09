# Games Database

## What is it?
This is a mini project to look at Cockroach DB using Spring JDBC, as well as an accompanying UI using React. That project can 
be found [here](https://github.com/chrisp1985/games-frontend). Cockroach isn't really needed for this as the consistency isn't
particularly important, but I wanted to dockerise it and understand how data is made highly available so there are multi- and 
single-cluster node setups in the docker directory.

## CockroachDB
CockroachDB offers high available and strong consistency by using the raft consensus algorithm. In the docker files, I have a
multi-cluster setup which aims to achieve consensus on changes made to the database, as well as a single-cluster model.

### Docker Compose
To run the database, just run `docker-compose up` on either of the docker-compose files in docker/cockroachdb.

The CockroachDB UI can be accessed at http://localhost:8080, while the database itself can be accessed through DBeaver or some
other SQL app via http://localhost:26527/gamesdb.

The Database is created using the script in the docker-compose file.

The UI shows some interesting stuff about usage, where the data is split etc.

### API
## Exception Handling
- [ ] TODO 

## Cache
- [ ] TODO

### Cache Evictions
- [ ] TODO

## JDBC
- [ ] TODO

## Flyway
Flyway creates the data in the database on startup. The first script creates the tables, the second populates them.

## MapStruct
### Notes
- To use Mapstruct, it has to go _after_ the implementation of lombok (see: https://stackoverflow.com/questions/64689460/mapstruct-mapper-returns-empty-mapped-object).

### How it's used
Run the `./gradlew clean build`, and the build directory will be populated with the mapper.

---

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.5/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.5/gradle-plugin/packaging-oci-image.html)
* [Spring Boot Testcontainers support](https://docs.spring.io/spring-boot/3.4.5/reference/testing/testcontainers.html#testing.testcontainers)
* [Testcontainers Postgres Module Reference Guide](https://java.testcontainers.org/modules/databases/postgres/)
* [JDBC API](https://docs.spring.io/spring-boot/3.4.5/reference/data/sql.html)
* [Spring Data JDBC](https://docs.spring.io/spring-boot/3.4.5/reference/data/sql.html#data.sql.jdbc)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.5/reference/web/servlet.html)
* [Flyway Migration](https://docs.spring.io/spring-boot/3.4.5/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [Prometheus](https://docs.spring.io/spring-boot/3.4.5/reference/actuator/metrics.html#actuator.metrics.export.prometheus)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/3.4.5/reference/actuator/index.html)
* [Testcontainers](https://java.testcontainers.org/)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

### Testcontainers support

This project uses [Testcontainers at development time](https://docs.spring.io/spring-boot/3.4.5/reference/features/dev-services.html#features.dev-services.testcontainers).

Testcontainers has been configured to use the following Docker images:

* [`postgres:latest`](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.

