package com.bylazar.panels.server

import fi.iki.elonen.NanoWSD.WebSocketFrame

abstract class SocketTask() {
    lateinit var send: (text: String) -> Unit

    abstract fun onOpen()
    abstract fun onClose()
    abstract fun onException()
    abstract fun onMessage(text: String)
    abstract fun onDetailedMessage(client: Socket.ClientSocket, pluginID: String, messageID: String, data: Any?)
    open fun onAdvancedMessage(message: WebSocketFrame){}
}