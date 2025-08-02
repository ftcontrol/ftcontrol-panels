package com.bylazar.gamepad

import com.bylazar.panels.Panels

object PanelsGamepad {
    val pkgName = this::class.java.`package`?.name ?: "null"

    fun getGamepad(): GamepadManager {
        val plugin = Panels.getPlugin(pkgName) as Plugin
        return plugin.manager
    }
}