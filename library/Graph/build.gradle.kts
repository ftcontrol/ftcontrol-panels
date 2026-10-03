val pluginNamespace = "com.bylazar.graph"
val pluginVersion = "1.0.6"

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
    name = "Graph"
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
    listOf("Inspection", "Blocks", "RobotCore", "RobotServer", "OnBotJava", "Hardware", "FtcCommon", "Vision").forEach {
        compileOnly("org.firstinspires.ftc:$it:12.0.0")
    }

    compileOnly(project(":Panels"))
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
                    description.set("Panels Graph Plugin")
                    name.set("Panels Graph")
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
