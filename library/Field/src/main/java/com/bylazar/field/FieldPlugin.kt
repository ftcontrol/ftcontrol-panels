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
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta

open class FieldPluginConfig : BasePluginConfig() {
    open var canvasUpdateInterval = 100L
    open var defaultBg: ImagePreset = PanelsField.images.INTO_THE_DEEP.DARK
}

class Plugin : Plugin<FieldPluginConfig>(FieldPluginConfig()) {
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
            { canvas -> send("canvasPacket", canvas) },
            { images -> send("canvasImages", images) }
        )
    }

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    lateinit var opModeManager: OpModeManagerImpl

    override fun onOpModeManager(o: OpModeManagerImpl) {
        this.opModeManager = o
    }

    override fun onOpModePreInit(opMode: OpMode) {
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