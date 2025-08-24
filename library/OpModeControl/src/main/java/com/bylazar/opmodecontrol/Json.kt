package com.bylazar.opmodecontrol

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta.Flavor

data class OpModeDetails(
    var name: String,
    var group: String,
    var flavour: Flavor,
    var source: OpModeMeta.Source,
    var defaultGroup: String,
    var autoTransition: String,
)

enum class OpModeStatus {
    INIT,
    RUNNING,
    STOPPED
}


data class OpModesList(
    val opModes: List<OpModeDetails>
)

data class ActiveOpMode(
    val opMode: OpModeDetails,
    val status: OpModeStatus,
    var startTimestamp: Long?
)


