package com.bylazar.battery

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit
import java.io.IOException
import java.util.Timer
import java.util.TimerTask
import kotlin.math.max

open class BatteryPluginConfig : BasePluginConfig() {
    open var test = "test"
}

class BatteryPlugin : Plugin<BatteryPluginConfig>(BatteryPluginConfig()) {
    override var id = "com.bylazar.battery"

    private var timer: Timer = Timer()

    var batteryVoltage = -1.0
    var lastBatteryVoltage: Double = 0.0

    fun updateBatteryVoltage() {
        batteryVoltage = -1.0

        val activeOpMode = opModeManager.activeOpMode
        val hardwareMap = activeOpMode?.hardwareMap

        if (hardwareMap != null) {
            for (module in hardwareMap.getAll(LynxModule::class.java)) {
                batteryVoltage = max(batteryVoltage, module.getInputVoltage(VoltageUnit.VOLTS))
            }
        } else {
            error("HW Map is null")
        }
    }

    lateinit var opModeManager: OpModeManagerImpl

    fun startSendingTime() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                try {
                    updateBatteryVoltage()
                    if (batteryVoltage != lastBatteryVoltage) {
                        send("battery", batteryVoltage)
                        lastBatteryVoltage = batteryVoltage
                    }
                } catch (e: IOException) {
                    stopTimer()
                }
            }
        }, 0, 5000)
    }

    fun stopTimer() {
        timer.cancel()
        timer.purge()
    }

    override fun onNewClient(client: Socket.ClientSocket) {
    }

    override fun onMessage(type: String, data: Any?) {
        log("Got message of type $type with data $data")
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {

    }

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
        opModeManager = o
        startSendingTime()
    }

    override fun onOpModePreInit(opMode: OpMode) {
    }

    override fun onOpModePreStart(opMode: OpMode) {
    }

    override fun onOpModePostStop(opMode: OpMode) {
    }


    override fun onEnablePanels() {
        startSendingTime()
    }

    override fun onDisablePanels() {
        stopTimer()
    }
}