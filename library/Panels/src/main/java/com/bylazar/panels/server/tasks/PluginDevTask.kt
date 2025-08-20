package com.bylazar.panels.server.tasks

import com.bylazar.panels.Logger
import com.bylazar.panels.plugins.PluginsManager
import com.bylazar.panels.server.Socket
import com.bylazar.panels.server.SocketTask


class PluginDevTask : SocketTask() {
    override fun onOpen() {

    }

    override fun onException() {

    }

    override fun onClose() {

    }

    override fun onMessage(text: String) {

    }

    override fun onDetailedMessage(
        client: Socket.ClientSocket,
        pluginID: String,
        messageID: String,
        data: Any?
    ) {
        if (pluginID == "core" && messageID == "pluginReloaded") {
            val stringData = try {
                data.toString()
            } catch (t: Throwable) {
                null
            }

            if (stringData == null) return

            if (stringData !in PluginsManager.plugins.keys) return

            val plugin = PluginsManager.plugins[stringData] ?: return

            plugin.onNewClient(client)

            Logger.socketLog("Reloaded plugin $stringData")
        }
    }
}