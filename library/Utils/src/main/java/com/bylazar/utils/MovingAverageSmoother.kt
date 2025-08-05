package com.bylazar.utils

class MovingAverageSmoother(private val windowSize: Int) {
    private val values = mutableListOf<Double>()
    private var sum = 0.0

    fun addValue(value: Double): Double {
        if (values.size == windowSize) {
            sum -= values.removeAt(0)
        }
        values.add(value)
        sum += value

        return sum / values.size
    }

    fun reset() {
        values.clear()
        sum = 0.0
    }
}