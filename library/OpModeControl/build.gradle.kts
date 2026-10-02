val pluginNamespace = "com.bylazar.opmodecontrol"
val pluginVersion = "1.0.4"

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
    version = "${dairyPublishing.version}+$pluginVersion"
}

meta {
    packagePath = pluginNamespace
    name = "OpModeControl"
    registerField("name", "String", "\"$pluginNamespace\"")
    registerField("clean", "Boolean") { "${dairyPublishing.clean}" }
    registerField("gitRef", "String") { "\"${dairyPublishing.gitRef}\"" }
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
    dairy {
        implementation(Sloth)
    }
}

dependencies {
    compileOnly(project(":Panels"))
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = pluginNamespace.substringBeforeLast('.') + ".sloth"
                artifactId = pluginNamespace.substringAfterLast('.')

                artifact(dairyDoc.dokkaHtmlJar)
                artifact(dairyDoc.dokkaJavadocJar)

                pom {
                    description.set("Panels OpModeControl Plugin")
                    name.set("Panels OpModeControl")
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
