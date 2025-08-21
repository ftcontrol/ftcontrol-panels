package com.bylazar.lights

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl

open class LightsPluginConfig : BasePluginConfig() {
    open var lightsUpdateInterval = 75L
}

object Plugin : Plugin<LightsPluginConfig>(LightsPluginConfig()) {
    val manager = LightsManager({ config }, { lightsState -> send("lightsPacket", lightsState) })

    override fun onNewClient(client: Socket.ClientSocket) {
        if(manager.lightsState.isEmpty()) return
        sendClient(client, "lightsPacket", manager.lightsState)
    }

    override fun onMessage(client: Socket.ClientSocket, type: String, data: Any?) {
        log("Got message of type $type with data $data")
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {
        manager.lights = listOf()
    }

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
    }

    override fun onOpModePreInit(opMode: OpMode) {
        manager.lights = listOf()
    }

    override fun onOpModePreStart(opMode: OpMode) {
    }

    override fun onOpModePostStop(opMode: OpMode) {
    }


    override fun onEnablePanels() {
    }

    override fun onDisablePanels() {
    }
}