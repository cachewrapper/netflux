plugins {
    id("java")
    id("java-library")
}

group = "org.cachewrapper"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    api(project(":network-controller"))
}