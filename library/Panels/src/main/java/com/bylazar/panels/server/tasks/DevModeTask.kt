package com.bylazar.panels.server.tasks

import com.bylazar.panels.Panels
import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.json.TimeData
import com.bylazar.panels.server.SocketTask
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer

class DevModeTask : SocketTask() {

    override fun onOpen() {
        send(
            SocketMessage(
                "core", "devServers", Panels.config.devPlugins
            ).toJson()
        )
    }

    override fun onException() {
    }

    override fun onClose() {
    }

    override fun onMessage(text: String) {

    }
}