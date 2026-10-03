pluginManagement {
    includeBuild("plugin-svelte-assets")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://repo.dairy.foundation/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
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
include(":CameraStream")
include(":FullPanels")
include(":Panels")
include(":Utils")
include(":Pinger")
include(":Graph")
include(":Lights")