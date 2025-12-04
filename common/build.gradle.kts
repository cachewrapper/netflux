plugins {
    id("java")
    id("java-library")
}

group = "org.cachewrapper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("org.redisson:redisson:3.52.0")
    api("com.esotericsoftware:kryo:5.6.2")

    api("io.hotmoka:toml4j:0.7.3")
    api("org.reflections:reflections:0.10.2")
    api("org.jetbrains:annotations:26.0.2")
    api("com.google.inject:guice:7.0.0")

    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}