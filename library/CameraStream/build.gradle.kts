val pluginNamespace = "com.bylazar.camerastream"
val pluginVersion = "1.0.2"

plugins {
    id("dev.frozenmilk.android-library") version "12.0.0-1.2.2"
    id("com.bylazar.svelte-assets")
    id("dev.frozenmilk.publish") version "0.1.0"
    id("dev.frozenmilk.doc") version "0.1.0"
    id("dev.frozenmilk.build-meta-data") version "0.1.0"
}

android.namespace = pluginNamespace

svelteAssets {
    assetsPath = assetPathForPlugin(pluginNamespace)
}

dairyPublishing {
    gitDir = file("..")
}

afterEvaluate {
    version = pluginVersion
}

meta {
    packagePath = pluginNamespace
    name = "CameraStream"
    registerField("name", "String", "\"$pluginNamespace\"")
    registerField("clean", "Boolean") { "${dairyPublishing.clean}" }
    registerField("gitRef", "String") { "\"$version\"" }
    registerField("snapshot", "Boolean") { "${dairyPublishing.snapshot}" }
    registerField("version", "String") { "\"${dairyPublishing.version}\"" }
}

ftc {
    kotlin()
    sdk {
        compileOnly(RobotCore)
        compileOnly(FtcCommon)
    }
}

dependencies {
    compileOnly(project(":Panels"))
    // TODO: remove?
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = pluginNamespace.substringBeforeLast('.')
                version = pluginVersion
                artifactId = pluginNamespace.substringAfterLast('.')

                artifact(dairyDoc.dokkaJavadocJar)
                artifact(dairyDoc.dokkaHtmlJar)

                pom {
                    description.set("Panels Camera Stream Plugin")
                    name.set("Panels Camera Stream")
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
    }
}
