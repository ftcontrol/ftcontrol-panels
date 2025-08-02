package com.bylazar.gamepad

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin
import com.bylazar.panels.server.Socket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl

open class GamepadPluginConfig : BasePluginConfig() {
}

class Plugin : Plugin<GamepadPluginConfig>(GamepadPluginConfig()) {
    val manager = GamepadManager()

    override fun onNewClient(client: Socket.ClientSocket) {
    }

    override fun onMessage(type: String, data: Any?) {
        log("Got message of type $type with data $data")
        if (type == "gamepad") {
            val changes = try {
                SocketMessage.convertData<Gamepad>(data)
            } catch (e: Exception) {
                log("Failed to convert data: ${e.message}")
                Gamepad()
            }

            if (changes == null) return

            if (changes.options) manager.setOptions()
        }
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {

    }

    override fun onAttachEventLoop(eventLoop: FtcEventLoop) {
    }

    override fun onOpModeManager(o: OpModeManagerImpl) {
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