package org.firstinspires.ftc.teamcode.utils

import com.bylazar.utils.LoopTimer

class LoopTimerExample {
    val timer = LoopTimer()

    fun main() {
        timer.start()
        Thread.sleep(100) // Simulate work
        timer.end()
        println("Time: ${timer.ms}ms, Freq: ${timer.hz}Hz")
    }
}