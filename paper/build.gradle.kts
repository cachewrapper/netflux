plugins {
    id("java")
    id("java-library")

    id("com.gradleup.shadow") version "9.2.2"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

group = "org.cachewrapper"
version = "1.0"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.10-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    implementation(project(":network-controller"))
}

tasks.shadowJar {
    archiveFileName.set("netflux-paper.jar")
    archiveClassifier.set("")
}