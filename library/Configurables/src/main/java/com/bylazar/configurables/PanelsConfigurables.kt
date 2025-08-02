package com.bylazar.configurables

import com.bylazar.panels.Panels

object PanelsConfigurables {
    val pkgName = this::class.java.`package`?.name ?: "null"

    fun refreshClass(cls: Any) {
        val name = cls::class.qualifiedName ?: return
        val plugin = Panels.getPlugin(pkgName) as Plugin
        plugin.refreshClass(name)
    }
}