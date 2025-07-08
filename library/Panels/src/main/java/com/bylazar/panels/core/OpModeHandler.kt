package com.bylazar.panels.core

import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta
import org.firstinspires.ftc.robotcore.internal.system.Misc

object OpModeHandler {
    lateinit var manager: OpModeManager

    fun init(m: OpModeManager) {
        manager = m
    }

    fun registerOpMode() {
        manager.register(
            OpModeMeta.Builder()
                .setName("Enable/Disable Panels")
                .setFlavor(OpModeMeta.Flavor.TELEOP)
                .setGroup("Panels")
                .build(),
            object : LinearOpMode() {
                @Throws(InterruptedException::class)
                override fun runOpMode() {
                    telemetry.log().add(
                        Misc.formatInvariant(
                            "Panels is currently %s. Press Start to %s it.",
                            if (PreferencesHandler.isEnabled) "enabled" else "disabled",
                            if (PreferencesHandler.isEnabled) "disable" else "enable"
                        )
                    )
                    telemetry.update()

                    waitForStart()

                    if (isStopRequested) {
                        return
                    }

                    Panels.toggle()
                }
            })

        Logger.coreLog("Registered Enable/Disable OpMode")
    }
}