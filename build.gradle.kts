plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.2.2"
}

allprojects {
    group = "cachewrapper.netflux"
    version = "1.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    val shadowModules = listOf(
        "paper",
        "paper-api",
        "velocity",
        "velocity-api"
    )

    if (project.name in shadowModules) {
        apply(plugin = "java")
        apply(plugin = "maven-publish")
        apply(plugin = "com.gradleup.shadow")

        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(25))
            }
        }

        tasks.shadowJar {
            archiveBaseName.set(project.name)
            archiveClassifier.set("all")
        }

        publishing {
            publications {
                register<MavenPublication>("gpr") {
                    artifact(tasks.named("shadowJar").get())
                    artifactId = project.name.lowercase()
                }
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