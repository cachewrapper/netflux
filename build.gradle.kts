plugins {
    java
    `maven-publish`
}

allprojects {
    group = "cachewrapper.netflux"
    version = "1.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    tasks.withType<Jar> {
        archiveBaseName.set(project.name)
    }

    publishing {
        publications {
            register<MavenPublication>("gpr") {
                from(components["java"])
                artifactId = project.name.lowercase()
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
