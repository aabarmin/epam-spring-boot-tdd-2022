import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("dev.abarmin.spring.tdd.java-conventions")
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("com.bmuschko.docker-remote-api") version "6.7.0"
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.apache.commons:commons-lang3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("io.cucumber:cucumber-java:7.5.0")
    testImplementation("io.cucumber:cucumber-spring:7.5.0")
    testImplementation("io.cucumber:cucumber-junit:7.5.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.5.0")
}

description = "e2e"

tasks.withType<BootJar> {
    enabled = false
}

tasks.withType<Test> {
    useJUnitPlatform()
    enabled = false // temporarily switching off

//    dependsOn(":app:bootBuildImage", startContainer.path)
//    finalizedBy(stopContainer)
}

val createContainer by tasks.creating(DockerCreateContainer::class) {
    targetImageId("app:0.0.1-SNAPSHOT")
    hostConfig.portBindings.set(listOf("8080:8080"))
    hostConfig.autoRemove.set(true)
}

val startContainer by tasks.creating(DockerStartContainer::class) {
    dependsOn(createContainer)
    targetContainerId(createContainer.containerId)
}

val stopContainer by tasks.creating(DockerStopContainer::class) {
    targetContainerId(createContainer.containerId)
}