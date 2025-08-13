pluginManagement {
    includeBuild("../library/plugin-svelte-assets")
    repositories {
        gradlePluginPortal()
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
    "Pinger"
)

modules.forEach { name ->
    include(":$name")
    project(":$name").projectDir = file("../library/$name")
}

include(":FtcRobotController")
include(":TeamCode")