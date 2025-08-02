package com.bylazar.panels.plugins

import android.content.Context
import com.bylazar.panels.GlobalStats
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.json.PluginDetails
import com.bylazar.panels.reflection.ClassFinder
import com.google.gson.Gson
import java.lang.ref.WeakReference
import kotlin.collections.set
import kotlin.jvm.java

object PluginsManager {
    val plugins: MutableMap<String, Plugin<*>> = mutableMapOf()
    val skippedPlugins: MutableMap<String, PluginDetails> = mutableMapOf()
    var isRegistered = false

    lateinit var contextRef: WeakReference<Context>

    fun loadPluginConfig(context: Context, filename: String = "config.json"): PluginDetails {
        val assetManager = context.assets
        assetManager.open(filename).use { inputStream ->
            val raw = inputStream.bufferedReader().readText()
            val gson = Gson()
            return gson.fromJson(raw, PluginDetails::class.java)
        }
    }

    fun init(context: Context) {
        contextRef = WeakReference(context)

        isRegistered = false
        val classes = ClassFinder.findClasses(
            { clazz ->
                Plugin::class.java.isAssignableFrom(clazz) && clazz != Plugin::class.java
            }
        )

        Logger.pluginsLog("Found ${classes.size} plugins.")

        classes.forEach {
            val clazz = Class.forName(it.className)

            val pluginInstance = clazz.getDeclaredConstructor().newInstance() as Plugin<*>

            Logger.pluginsLog("Got ${pluginInstance.panelsPluginUniqueID}.")

            //CONFIG
            val configs = ClassFinder.findClasses(
                { clazz ->
                    pluginInstance.config::class.java.isAssignableFrom(clazz) && clazz != pluginInstance.config::class.java
                }
            )

            Logger.pluginsLog("Found ${configs.size} configs.")

            if (configs.isNotEmpty()) {
                val clazz = Class.forName(configs[0].className)
                val newConfig = clazz.getDeclaredConstructor().newInstance()
                pluginInstance.setConfig(newConfig)
                Logger.pluginsLog("Set new config.")
            }

            if (!pluginInstance.config.isEnabled) {
                Logger.pluginsLog("Is Disabled ID '${pluginInstance.panelsPluginUniqueID}'")
            } else {
                try {
                    val details: PluginDetails =
                        loadPluginConfig(context, "web/plugins/${pluginInstance.panelsPluginUniqueID}/config.json")

                    pluginInstance.details = details

                    Logger.pluginsLog(pluginInstance.details.toString())

                    Logger.pluginsLog("Got details or ID '${pluginInstance.panelsPluginUniqueID}'")

                    if (pluginInstance.details.pluginsCoreVersion != GlobalStats.pluginsCoreVersion) {
                        skippedPlugins[pluginInstance.panelsPluginUniqueID] = pluginInstance.details
                        Logger.pluginsLog("Skipped plugin: ${clazz.name} with ID '${pluginInstance.panelsPluginUniqueID}', coreVersion: ${pluginInstance.details.pluginsCoreVersion}, latest: ${GlobalStats.pluginsCoreVersion}")
                    } else {
                        plugins[pluginInstance.panelsPluginUniqueID] = pluginInstance
                        Logger.pluginsLog("Successfully registered plugin: ${clazz.name} with ID '${pluginInstance.panelsPluginUniqueID}'")
                    }

                } catch (t: Throwable) {
                    Logger.pluginsError("Error while loading: ${clazz.name} with ID '${pluginInstance.panelsPluginUniqueID}', ${t.message}")
                }
            }


        }

        isRegistered = true

        plugins.values.forEach {
            it.registerInternal(Panels, context)
            it.log("Successfully registered plugin")
        }
    }
}
