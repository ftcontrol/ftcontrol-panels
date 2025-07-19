package com.bylazar.field

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.core.OpModeHandler.manager
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl

open class FieldPluginConfig : BasePluginConfig() {
    open var canvasUpdateInterval = 100L
}

class FieldPlugin : Plugin<FieldPluginConfig>(FieldPluginConfig()) {
    override var id = "com.bylazar.field"

    lateinit var manager: FieldManager

    override fun onNewClient(client: Socket.ClientSocket) {
        sendClient(client, "canvasPacket", manager.lastCanvas)
        sendClient(client, "canvasImages", manager.images)
    }

    override fun onMessage(type: String, data: Any?) {
        log("Got message of type $type with data $data")
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {
        manager = FieldManager(
            config,
            { send("canvasPacket", manager.canvas) },
            { send("canvasImages", manager.images) }
        )
    }

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
    }

    override fun onOpModePreInit(opMode: OpMode) {
        manager.update()
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