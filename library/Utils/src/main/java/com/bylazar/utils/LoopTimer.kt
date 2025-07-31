package com.bylazar.ftcontrol

class LoopTimer(smootherWindow: Int = 5) {
    var startTime = 0L
        private set
    var endTime = 0L
        private set
    var ms: Long = 0L
        private set
    var hz: Double = 0.0
        private set

    private var innerStartTime = 0L
    private var innerEndTime = 0L

    private val smoother = MovingAverageSmoother(smootherWindow)


    fun start() {
        innerStartTime = System.currentTimeMillis()
    }

    fun end() {
        innerEndTime = System.currentTimeMillis()
        startTime = innerStartTime
        endTime = innerEndTime
        ms = endTime - startTime
        val rawHz = if (ms > 0) 1000.0 / ms else 1000.0
        hz = smoother.addValue(rawHz)
    }
}