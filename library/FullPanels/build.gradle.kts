val pluginNamespace = "com.bylazar.fullpanels"
val pluginVersion = "1.0.4"

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    id("com.bylazar.svelte-assets")
}

svelteAssets {
    webAppPath = "web"
    buildDirPath = "dist"
    assetsPath = "web/plugins/$pluginNamespace"
}

android {
    namespace = pluginNamespace

    defaultConfig {
        compileSdk = 34
        minSdk = 24
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    publishing {
        singleVariant("release") {}
    }
}

dependencies {
    compileOnly("org.firstinspires.ftc:Inspection:10.3.0")
    compileOnly("org.firstinspires.ftc:Blocks:10.3.0")
    compileOnly("org.firstinspires.ftc:RobotCore:10.3.0")
    compileOnly("org.firstinspires.ftc:RobotServer:10.3.0")
    compileOnly("org.firstinspires.ftc:OnBotJava:10.3.0")
    compileOnly("org.firstinspires.ftc:Hardware:10.3.0")
    compileOnly("org.firstinspires.ftc:FtcCommon:10.3.0")
    compileOnly("org.firstinspires.ftc:Vision:10.3.0")

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
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = pluginNamespace.substringBeforeLast('.')
                artifactId = pluginNamespace.substringAfterLast('.')
                version = pluginVersion

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

        repositories {
            maven {
                name = "localDevRepo"
                url = uri("file:///C:/Users/lazar/Documents/GitHub/ftcontrol-maven/releases")
            }
        }
    }
}
