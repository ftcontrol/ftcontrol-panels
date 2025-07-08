package com.bylazar.opmodecontrol

import android.content.Context
import com.bylazar.panels.Panels
import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin

open class OpModeControlPluginConfig : BasePluginConfig() {
    open var test = "test"
}

class OpModeControlPlugin : Plugin<BasePluginConfig>(OpModeControlPluginConfig()) {
override var id = "com.bylazar.opmodecontrol"
    override fun onRegister(
        panelsInstance: Panels,
        context: Context
    ) {

    }
}