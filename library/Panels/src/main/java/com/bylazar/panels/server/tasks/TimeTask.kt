package com.bylazar.panels.server.tasks

import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.json.TimeData
import com.bylazar.panels.server.SocketTask
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class TimeTask : SocketTask() {
    private var timer: Timer = Timer()

    override fun onOpen() {
        startSendingTime()
    }

    override fun onException() {
        stopTimer()
    }

    override fun onClose() {
        stopTimer()
    }

    override fun onMessage(text: String) {

    }

    fun startSendingTime() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                try {
                    val time = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(Date())

                    val payload = TimeData(time)
                    val message = SocketMessage("core", "time", payload)

                    send(message.toJson())
                } catch (e: IOException) {
                    stopTimer()
                }
            }
        }, 0, 1000)
    }

    fun stopTimer() {
        timer.cancel()
        timer.purge()
    }
}