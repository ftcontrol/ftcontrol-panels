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
import kotlin.math.abs

open class GamepadPluginConfig : BasePluginConfig() {
}

object Plugin : Plugin<GamepadPluginConfig>(GamepadPluginConfig()) {
    val firstManager = GamepadManager()
    val secondManager = GamepadManager()

    private var lastSendTime = 0L
    private val sendIntervalMs = 500L

    override fun onNewClient(client: Socket.ClientSocket) {
        sendClient(client, "newGamepad0", firstManager.currentState)
        sendClient(client, "newGamepad1", secondManager.currentState)
    }

    override fun onMessage(type: String, data: Any?) {
        log("Got message of type $type with data $data")
        if (type == "gamepad0" || type == "gamepad1") {
            val changes = try {
                SocketMessage.convertData<Gamepad>(data)
            } catch (e: Exception) {
                log("Failed to convert data: ${e.message}")
                Gamepad()
            }

            if (changes == null) return

            if (abs(changes.leftStick.x) < 0.01) changes.leftStick.x = 0.0
            if (abs(changes.leftStick.y) < 0.01) changes.leftStick.y = 0.0
            if (abs(changes.rightStick.x) < 0.01) changes.rightStick.x = 0.0
            if (abs(changes.rightStick.y) < 0.01) changes.rightStick.y = 0.0

            when(type){
                "gamepad0" -> {
                    firstManager.update(changes)
                }
                "gamepad1" -> {
                    secondManager.update(changes)
                }
            }

            val now = System.currentTimeMillis()
            if (now - lastSendTime >= sendIntervalMs) {
                when(type){
                    "gamepad0" -> {
                        send("newGamepad0", firstManager.currentState)
                    }
                    "gamepad1" -> {
                        send("newGamepad1", secondManager.currentState)
                    }
                }
                lastSendTime = now
            }
        }
    }

    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {
        send("newGamepad0", firstManager.currentState)
        send("newGamepad1", secondManager.currentState)
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