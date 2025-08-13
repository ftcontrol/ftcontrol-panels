package org.firstinspires.ftc.teamcode.utils

import com.bylazar.utils.MovingAverageSmoother

class MovingAverageExample {
    val smoother = MovingAverageSmoother(3)

    fun main() {
        smoother.addValue(10.0) // 10.0
        smoother.addValue(20.0) // 15.0
        smoother.addValue(30.0) // 20.0
        smoother.addValue(40.0) // 30.0 (oldest value 10.0 removed)
    }

    fun getValue(): Double {
        return smoother.value
    }
}