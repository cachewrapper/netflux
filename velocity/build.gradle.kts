plugins {
    id("java")
    id("com.gradleup.shadow") version "9.2.2"
}

group = "org.cachewrapper"
version = "1.0"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenCentral()
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    implementation(project(":network-controller"))
    implementation(project(":Api:velocity-api"))
}

tasks.shadowJar {
    archiveFileName.set("netflux-velocity-$version.jar")
    archiveClassifier.set("")
}