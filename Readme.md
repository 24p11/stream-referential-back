# Load Referential Project

## Requirements

**Java 11 (wip)**  
https://www.oracle.com/java/technologies/javase-jdk11-downloads.html  
https://adoptopenjdk.net/?variant=openjdk11 (.msi)

**Java 8 (old)**
https://www.oracle.com/java/technologies/javase-jdk8-downloads.html  
https://adoptopenjdk.net/?variant=openjdk8 (.msi)

**Maven**
https://maven.apache.org/

### Download and run project

`git clone https://github.com/24p11/stream-referential-back.git`

`mvn clean install` or `mvn clean install -DskipTests`

`cd target`

`java -jar load-<VERSION>.jar --spring.config.location=<path>/application.yml`