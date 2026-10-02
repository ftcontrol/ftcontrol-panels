plugins {
    id("dev.frozenmilk.teamcode") version "12.0.0-1.2.2"
}

ftc {
    kotlin()
    sdk.TeamCode()
}

dependencies {
    implementation(project(":FullPanels"))
}