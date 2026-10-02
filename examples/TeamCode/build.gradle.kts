plugins {
    id("dev.frozenmilk.teamcode") version "11.1.0-1.1.1"
}

ftc {
    kotlin()
    sdk.TeamCode()
    dairy.ftControl {
        implementation(fullpanels(""))
    }
}
