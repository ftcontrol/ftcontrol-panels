package com.bylazar.gamepad

import com.bylazar.panels.Panels

object PanelsGamepad {
    fun getGamepad(): GamepadManager {
        val plugin = Panels.getPlugin(GamepadPlugin().id) as GamepadPlugin
        return plugin.manager
    }
}