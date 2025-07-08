package com.bylazar.panels.plugins

import android.R.attr.type
import android.content.Context
import android.provider.Browser.sendString
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.json.createSocketMessage
import com.bylazar.panels.server.Socket
import com.bylazar.panels.server.Socket.ClientSocket
import com.qualcomm.ftccommon.FtcEventLoop
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl


abstract class Plugin<T : BasePluginConfig>(baseConfig: T) {
    var config = baseConfig
    var details = PluginDetails()
    val isDev: Boolean
        get() = config.isDev
    val isEnabled: Boolean
        get() = config.isEnabled

    internal fun setConfig(newConfig: Any) {
        @Suppress("UNCHECKED_CAST")
        config = newConfig as T
    }

    fun log(message: String) {
        Logger.log("${Logger.PLUGINS_PREFIX}/${id}", message)
    }

    fun error(message: String) {
        Logger.error("${Logger.PLUGINS_PREFIX}/${id}", message)
    }

    fun send(type: String, data: Any) {
        val message = createSocketMessage(id, type, data)
        Panels.socket.sendStrings(message.toJson())
    }

    fun sendClient(client: Socket.ClientSocket, type: String, data: Any) {
        val message = createSocketMessage(id, type, data)
        client.sendString(message.toJson())
    }

    abstract var id: String

    abstract fun onRegister(panelsInstance: Panels, context: Context)
    abstract fun onAttachEventLoop(eventLoop: FtcEventLoop)
    abstract fun onOpModeManager(opModeManager: OpModeManagerImpl)
    abstract fun onOpModePreInit(opMode: OpMode)
    abstract fun onOpModePreStart(opMode: OpMode)
    abstract fun onOpModePostStop(opMode: OpMode)
    abstract fun onNewClient(client: ClientSocket)
}