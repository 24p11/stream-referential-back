# Load Referential Project

## Requirements

**Java 8**
https://www.oracle.com/java/technologies/javase-jdk8-downloads.html

**Maven**
https://maven.apache.org/

### Download and run project

`git clone https://github.com/24p11/stream-referential-back.git`

`mvn clean install` or `mvn clean install -DskipTests`

`cd target`

`java -jar load-<VERSION>.jar --spring.config.location=<path>/application.yml`