plugins {
    id("java")
    id("java-library")

    id("com.gradleup.shadow") version "9.2.2"
}

group = "org.cachewrapper"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    api(project(":Api:common-api"))
}

tasks.shadowJar {
    archiveFileName.set("netflux-velocity-api.jar")
    archiveClassifier.set("")
}