# embedded-mongo-spring

[![Build Status](https://travis-ci.org/mwarc/embedded-mongo-spring.svg?branch=master)](https://travis-ci.org/mwarc/embedded-mongo-spring)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mwarc/embedded-mongo-spring/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mwarc/embedded-mongo-spring)

## Overview

The Spring TestContext Framework provides annotation-driven unit and integration testing support. 
Fongo is an in-memory java implementation of MongoDB.

embedded-mongo-spring provides a way to use fongo with Spring TestContext Framework. 

## Annotation

The magic is in FongoTestExecutionListener class (default listener) which implements TestExecutionListener. This listener find @EmbeddedMongo annotation:

    @EmbeddedMongo: annotation to start an embedded mongodb server
    
*   dbName: database name; "myDbName" by default

## Configuration

In your pom.xml, you have to add embedded-mongo-spring maven dependency:

    <dependency>
        <groupId>com.github.mwarc</groupId>
        <artifactId>embedded-mongo-spring</artifactId>
        <version>0.1.0</version>
    </dependency>


or when you use gradle add to build.gradle:

    dependencies {
        testCompile 'com.github.mwarc:embedded-mongo-spring:0.1.0'
    }

## Use cases

### JUnit:

The following snippet use basic Spring configuration and @EmbeddedMongo. 
Default listener FongoTestExecutionListener find @EmbeddedMongo annotation 
and try to start an embedded mongodb (database myDbName).

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TestExecutionListeners({
    FongoTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class})
@EmbeddedMongo(dbName = "myDbName")
public class EmbeddedMongoTest {
    @Test
    public void shouldSaveUserAndRetrieveItFromMongo() {
        ...
    }
}
```

### Spock:

The following snippet use basic Spring configuration and @EmbeddedMongo. 
Default listener FongoTestExecutionListener find @EmbeddedMongo annotation 
and try to start an embedded mongodb with default configuration.

```groovy
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@EmbeddedMongo
class EmbeddedMongoSpec extends Specification {
    def "should save user and retrieve it from mongo"() {
        ...
    }
}
```

## Building

* Clone the repository
* Run `./gradlew clean build` (on Linux/Mac) or `gradlew.bat clean build` (on Windows)

## Licence

Apache Licence v2.0 (see LICENCE.txt)