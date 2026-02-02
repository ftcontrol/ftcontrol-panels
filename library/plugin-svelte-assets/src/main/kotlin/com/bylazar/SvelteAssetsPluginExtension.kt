package com.bylazar

abstract class SvelteAssetsPluginExtension {
    fun assetPathForPlugin(pluginNamespace: String) = "web/plugins/$pluginNamespace"
    var assetsPath: String? = null
    var webAppPath: String = "web"
    var buildDirPath: String = "dist"
}