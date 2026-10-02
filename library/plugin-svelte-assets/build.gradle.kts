plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.bylazar"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
}

dependencies {
    //noinspection AndroidGradlePluginVersion
    compileOnly("com.android.tools.build:gradle:8.7.0")
}

gradlePlugin {
    plugins {
        create("svelteAssetsPlugin") {
            id = "com.bylazar.svelte-assets"
            implementationClass = "com.bylazar.SvelteAssetsPlugin"
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("plugin") {
                from(components["java"])

                groupId = project.group.toString()
                artifactId = "svelte-assets"
                version = project.version.toString()

                pom {
                    name.set("Svelte Assets Plugin")
                    description.set("A Gradle plugin to bundle Svelte apps into Android libraries.")
                    url.set("https://panels.bylazar.com")

                    developers {
                        developer {
                            id.set("lazar")
                            name.set("Lazar Dragos George")
                            email.set("hi@bylazar.com")
                        }
                    }
                }
            }
        }

        repositories {
            maven {
                name = "localDevRepo"
                url = uri("file:///C:/Users/lazar/Documents/GitHub/ftcontrol-maven/releases")
            }
        }
    }
}