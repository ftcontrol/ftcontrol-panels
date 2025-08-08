package com.bylazar.gamepad

object PanelsGamepad {
    val firstManager: GamepadManager
        get() = Plugin.firstManager
    val secondManager: GamepadManager
        get() = Plugin.secondManager
}