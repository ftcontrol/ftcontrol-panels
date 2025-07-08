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

data class OpModesList(
    val opModes: List<OpModeDetails>
)


