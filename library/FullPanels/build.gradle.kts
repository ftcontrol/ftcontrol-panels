val pluginNamespace = "com.bylazar.fullpanels"
val pluginVersion = "1.0.12"

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
    name = "FullPanels"
    registerField("name", "String", "\"$pluginNamespace\"")
    registerField("clean", "Boolean") { "${dairyPublishing.clean}" }
    registerField("gitRef", "String") { "\"${dairyPublishing.gitRef}\"" }
    registerField("snapshot", "Boolean") { "${dairyPublishing.snapshot}" }
    registerField("version", "String") { "\"$version\"" }
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
    api(project(":Panels"))

    api(project(":OpModeControl"))
    api(project(":Telemetry"))
    api(project(":Configurables"))
    api(project(":Themes"))
    api(project(":Capture"))
    api(project(":LimelightProxy"))
    api(project(":Gamepad"))
    api(project(":Field"))
    api(project(":Docs"))
    api(project(":Battery"))
    api(project(":Utils"))
    api(project(":Pinger"))
    api(project(":Graph"))
    api(project(":Lights"))
    api(project(":CameraStream"))
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
                    description.set("All in one toolbox dashboard for FTC with core plugins.")
                    name.set("Full Panels")
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
