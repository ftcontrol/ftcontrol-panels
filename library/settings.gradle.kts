pluginManagement {
    includeBuild("plugin-svelte-assets")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://repo.dairy.foundation/releases")
    }
}

include(":TeamCode")
include(":OpModeControl")
include(":ExamplePlugin")
include(":Telemetry")
include(":Configurables")
include(":Themes")
include(":Capture")
include(":LimelightProxy")
include(":Field")
include(":Gamepad")
include(":Docs")
include(":Battery")
include(":FullPanels")
include(":Panels")
include(":Utils")
include(":Pinger")
include(":Graph")
include(":Lights")
include(":CameraStream")