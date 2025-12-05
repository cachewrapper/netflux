import java.util.Locale

plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.2.2"
}

group = "cachewrapper.netflux"
version = "1.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

val platform: String? by project

val modulesToBuild = when (platform?.lowercase()) {
    "velocity" -> listOf("velocity-api")
    "paper" -> listOf("paper-api")
    else -> listOf("paper-api", "velocity-api")
}

modulesToBuild.forEach { mod ->
    val shadowTask = tasks.register<Jar>("shadow${mod.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}") {
        archiveBaseName.set(mod)
        archiveClassifier.set("all")
        from(sourceSets.main.get().output)
    }

    publishing {
        publications {
            register<MavenPublication>(mod) {
                artifact(shadowTask.get())
                artifactId = mod
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/cachewrapper/netflux")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                    password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    if (modulesToBuild.contains("velocity-api")) {
        compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
        annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
        compileOnly("com.github.cachewrapper.netflux:velocity-api:1.0")
    }

    if (modulesToBuild.contains("paper-api")) {
        compileOnly("com.github.cachewrapper.netflux:paper-api:1.0")
    }
}
