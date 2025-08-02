package com.bylazar.battery

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

open class BatteryPluginConfig : BasePluginConfig() {
    open var test = "test"
}

class Plugin : Plugin<BatteryPluginConfig>(BatteryPluginConfig()) {
    private var timer: Timer = Timer()

    var provider = BatteryProvider()

    lateinit var opModeManager: OpModeManagerImpl

    fun startSendingTime() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                try {
                    provider.updateBatteryVoltage(opModeManager)
                    if (provider.hasChanged) {
                        send("battery", provider.batteryVoltage)
                        provider.lastBatteryVoltage = provider.batteryVoltage
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