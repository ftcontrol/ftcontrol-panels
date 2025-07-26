package com.bylazar.gamepad

class GamepadManager {
    val DELETION_INTERVAL = 50L + 10L

    private var gamepad = Gamepad()
    private var timestamps = GamepadTimestamps()

    internal fun setOptions(){
        gamepad.options = true
        timestamps.options = System.currentTimeMillis()
    }

    val options: Boolean
        get() = gamepad.options && System.currentTimeMillis() - timestamps.options <= DELETION_INTERVAL
}