package com.bylazar.opmodecontrol

import com.bylazar.panels.plugins.BasePluginConfig
import com.bylazar.panels.plugins.Plugin

open class OpModeControlPluginConfig : BasePluginConfig() {
    open var test = "test"
}

class OpModeControlPlugin : Plugin<BasePluginConfig>(OpModeControlPluginConfig()) {

}