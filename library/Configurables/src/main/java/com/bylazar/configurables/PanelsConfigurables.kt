package com.bylazar.configurables

import com.bylazar.panels.Panels

object PanelsConfigurables {
    fun refreshClass(cls: Any) {
        val name = cls::class.qualifiedName ?: return
        val plugin = Panels.getPlugin(ConfigurablesPlugin().panelsPluginUniqueID) as ConfigurablesPlugin
        plugin.refreshClass(name)
    }
}