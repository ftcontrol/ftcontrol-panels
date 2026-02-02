val pluginNamespace = "com.bylazar.limelightproxy"
val pluginVersion = "1.0.4"

plugins {
    id("dev.frozenmilk.android-library") version "11.1.0-1.1.1"
    id("com.bylazar.svelte-assets")
    id("dev.frozenmilk.publish") version "0.0.5"
    id("dev.frozenmilk.doc") version "0.0.5"
    id("dev.frozenmilk.build-meta-data") version "0.0.2"
}

android.namespace = pluginNamespace

svelteAssets {
    assetsPath = assetPathForPlugin(pluginNamespace)
}

dairyPublishing {
    gitDir = file("..")
}

version = "${dairyPublishing.version}+$pluginVersion"

meta {
    packagePath = pluginNamespace
    name = "LimelightProxy"
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
        compileOnly(RobotServer)
    }
}

dependencies {
    compileOnly(project(":Panels"))

    implementation("com.squareup.okhttp3:okhttp:4.10.0")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = pluginNamespace.substringBeforeLast('.') + ".sloth"
                artifactId = pluginNamespace.substringAfterLast('.')

                artifact(dairyDoc.dokkaJavadocJar)
                artifact(dairyDoc.dokkaHtmlJar)

                pom {
                    description.set("Panels Limelight Proxy Plugin")
                    name.set("Panels Limelight Proxy")
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
