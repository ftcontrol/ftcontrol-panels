package com.bylazar.field

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl

open class FieldPluginConfig : BasePluginConfig() {
    open var canvasUpdateInterval = 100L
    open var defaultBg: ImagePreset = PanelsField.images.INTO_THE_DEEP.DARK
}

object Plugin : Plugin<FieldPluginConfig>(FieldPluginConfig()) {
    var manager = FieldManager()

    override fun onNewClient(client: Socket.ClientSocket) {
        sendClient(client, "canvasPacket", manager.lastCanvas)
        if(manager.images.isNotEmpty()) sendClient(client, "canvasImages", manager.images)

        log("Images length: ${manager.images.keys.size}")
    }

    override fun onMessage(client: Socket.ClientSocket, type: String, data: Any?) {
        log("Got message of type $type with data $data")
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {
        manager.config = config
        manager.sendCanvas = { canvas -> send("canvasPacket", canvas) }
        manager.sendImages = { images -> if(images.isNotEmpty()) send("canvasImages", images) }

        log("Images length: ${manager.images.keys.size}")
    }

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
    }

    override fun onOpModePreInit(opMode: OpMode) {
        manager.setOffsets(PanelsField.PANELS)
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