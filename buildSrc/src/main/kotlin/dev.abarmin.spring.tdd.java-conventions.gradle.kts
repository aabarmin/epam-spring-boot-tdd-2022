plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

group = "dev.abarmin.spring.tdd"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}