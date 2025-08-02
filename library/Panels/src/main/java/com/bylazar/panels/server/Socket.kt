package com.bylazar.panels.server

import com.bylazar.panels.Logger
import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.plugins.PluginsManager
import com.bylazar.panels.server.tasks.TimeTask
import fi.iki.elonen.NanoWSD
import java.io.IOException
import java.util.Timer
import java.util.TimerTask

class Socket(
    port: Int,
) : NanoWSD(port) {
    override fun openWebSocket(handshake: IHTTPSession): WebSocket {
        return ClientSocket(handshake)
    }

    private val clients: MutableSet<ClientSocket> = mutableSetOf()

    fun sendStrings(data: String) {
        try {
            clients.forEach {
                it.send(data)
            }
            Logger.socketLog("Sent to all clients: $data")
        } catch (e: Exception) {
            Logger.socketError("Failed to send to all clients: $data")
        }
    }

    inner class ClientSocket(handshake: IHTTPSession) : WebSocket(handshake) {
        val tasks: List<SocketTask> = listOf(
            TimeTask(),
        )

        internal fun sendString(data: String) {
            try {
                send(data)
            } catch (e: IOException) {
                Logger.socketError("Error sending message to client: ${e.message}")
            } catch (t: Throwable){
                Logger.socketError("Error sending message to client: ${t.message}")
            }
        }

        init {
            tasks.forEach {
                it.send = { text -> sendString(text) }
            }
        }

        private var ping: TimerTask? = null

        override fun onOpen() {
            Logger.socketLog("onOpen: Connection opened.")

            val startTime = System.currentTimeMillis()

            Logger.socketLog("onOpen: Running task onOpen() callbacks...")
            tasks.forEach {
                Logger.socketLog("onOpen: Task ${it.javaClass.simpleName} onOpen()")
                it.onOpen()
            }

            Logger.socketLog("onOpen: Adding client to list...")
            clients.add(this)

            Logger.socketLog("onOpen: Notifying plugins (${PluginsManager.plugins.size} found)...")
            PluginsManager.plugins.values.forEach { plugin ->
                Logger.socketLog("onOpen: Notifying plugin ${plugin.javaClass.simpleName}")
                plugin.newClientInternal(this)
            }

            if (ping == null) {
                Logger.socketLog("onOpen: Setting up ping timer...")
                ping = object : TimerTask() {
                    override fun run() {
                        try {
                            ping("LAZAR".toByteArray())
                        } catch (e: IOException) {
                            Logger.socketError("ping: IOException occurred. Cancelling ping. Error: ${e.message}")
                            ping!!.cancel()
                        }
                    }
                }

                Timer().schedule(ping, 1000, 2000)
                Logger.socketLog("onOpen: Ping timer scheduled (start in 1s, interval 2s).")
            } else {
                Logger.socketLog("onOpen: Ping already set.")
            }

            val endTime = System.currentTimeMillis()
            Logger.socketLog("onOpen: Completed in ${endTime - startTime} ms.")
        }

        override fun onClose(
            code: WebSocketFrame.CloseCode,
            reason: String,
            initiatedByRemote: Boolean
        ) {
            Logger.socketError("WebSocket closed: $code, reason: $reason")
            tasks.forEach { it.onClose() }

            ping?.cancel();

            clients.remove(this)
        }

        override fun onMessage(message: WebSocketFrame) {
            Logger.socketLog("Received message: ${message.textPayload}")
            try {
                tasks.forEach { it.onMessage(message.textPayload) }
                tasks.forEach { it.onAdvancedMessage(message) }

                val message = SocketMessage.fromJson(message.textPayload)

                val pluginID = message.pluginID

                val matchedPluginKey = PluginsManager.plugins.keys.find { it == pluginID }

                matchedPluginKey?.let { key ->
                    val plugin = PluginsManager.plugins[key]
                    plugin?.onMessage(message.messageID, message.data)
                }

            } catch (e: Exception) {
                Logger.socketError("Error handling message ${message.textPayload}: ${e.message}")
                close(
                    WebSocketFrame.CloseCode.InternalServerError,
                    "Error in message handling",
                    false
                )
            }
        }

        override fun onPong(pong: WebSocketFrame) {}

        override fun onException(exception: IOException) {
            tasks.forEach { it.onException() }
        }
    }

    fun startServer() {
        try {
            start()
            Logger.serverLog("Socket started on port $listeningPort")
        } catch (e: IOException) {
            Logger.serverLog("Failed to start server: ${e.message}")
        }
    }

    fun stopServer() {
        stop()
        Logger.serverLog("Socket stopped")
    }
}
