pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven("https://repo.dairy.foundation/releases")
    }
}

rootProject.name = "examples"

val modules = listOf(
    "OpModeControl",
    "ExamplePlugin",
    "Telemetry",
    "Configurables",
    "Themes",
    "Capture",
    "LimelightProxy",
    "Field",
    "Gamepad",
    "Docs",
    "Battery",
    "FullPanels",
    "Panels",
    "Utils",
    "Pinger",
    "Graph",
    "Lights",
    "CameraStream"
)

//modules.forEach { name ->
//    include(":$name")
//    project(":$name").projectDir = file("../library/$name")
//}

includeBuild("../library") {
    dependencySubstitution {
        substitute(module("com.bylazar.sloth:fullpanels")).using(project(":FullPanels"))
        substitute(module("com.bylazar:fullpanels")).using(project(":FullPanels"))
    }
}