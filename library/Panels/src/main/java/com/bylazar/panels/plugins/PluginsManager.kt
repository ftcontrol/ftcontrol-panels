package com.bylazar.panels.plugins

import android.content.Context
import com.bylazar.panels.GlobalStats
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.Panels.server
import com.bylazar.panels.TaskTimer
import com.bylazar.panels.json.PluginDetails
import com.bylazar.panels.json.SocketMessage.Companion.gson
import com.bylazar.panels.reflection.ClassFinder
import com.google.gson.Gson
import java.io.IOException
import java.lang.ref.WeakReference
import kotlin.collections.set
import kotlin.jvm.java

object PluginsManager {
    val plugins: MutableMap<String, Plugin<*>> = mutableMapOf()
    val skippedPlugins: MutableMap<String, PluginDetails> = mutableMapOf()
    val skippedPluginsStrings: MutableMap<String, String> = mutableMapOf()
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

    fun loadPluginConfigString(context: Context, id: String): String {
        val assetManager = context.assets
        assetManager.open("web/plugins/${id}/config.json").use { inputStream ->
            val raw = inputStream.bufferedReader().readText()
            return raw
        }
    }

    fun init(context: Context) {
        contextRef = WeakReference(context)

        val assetManager = context.assets

        isRegistered = false

        val pluginsRoot = "web/plugins"
        var pluginDirs = try {
            assetManager.list(pluginsRoot)
                ?.filter { filename ->
                    val path = "$pluginsRoot/$filename"
                    assetManager.list(path)?.isNotEmpty() == true
                }
                ?: emptyList<String>()
        } catch (e: IOException) {
            Logger.pluginsError("Error while fetching ids: ${e.message}")
            emptyList<String>()
        }

        pluginDirs = pluginDirs.mapNotNull {
            try {
                val clazz = Class.forName("${it}.Plugin")
                it
            } catch (t: Throwable) {
                null
            }
        }

        for (dir in pluginDirs) {
            Logger.pluginsLog("Found plugin folder: $dir")
        }
        Logger.pluginsLog("Found ${pluginDirs.size} plugins.")

        pluginDirs.forEach {
            val clazz = Class.forName("${it}.Plugin")

            @Suppress("UNCHECKED_CAST")
            val pluginInstance = clazz.kotlin.objectInstance as? Plugin<*>

            if (pluginInstance == null) {
                Logger.pluginsError("$it plugin is not kotlin singleton")
                return@forEach
            }

            Logger.pluginsLog("Got ${it}.")

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
                Logger.pluginsLog("Is Disabled ID '${it}'")
            } else {
                try {
                    val details: PluginDetails =
                        loadPluginConfig(context, "web/plugins/${it}/config.json")

                    pluginInstance.details = details

                    Logger.pluginsLog(pluginInstance.details.toString())

                    Logger.pluginsLog("Got details or ID '${it}'")

                    val configString = loadPluginConfigString(context, it)

                    if (pluginInstance.details.pluginsCoreVersion != GlobalStats.pluginsCoreVersion && !pluginInstance.details.pluginsCoreVersion.startsWith("link:")) {
                        skippedPlugins[it] = pluginInstance.details
                        skippedPluginsStrings[it] = configString
                        Logger.pluginsLog("Skipped plugin: ${clazz.name} with ID '${it}', coreVersion: ${pluginInstance.details.pluginsCoreVersion}, latest: ${GlobalStats.pluginsCoreVersion}")
                    } else {
                        plugins[it] = pluginInstance
                        pluginInstance.detailsString = configString

                        Logger.pluginsLog("Successfully registered plugin: ${clazz.name} with ID '${it}'")
                    }

                } catch (t: Throwable) {
                    Logger.pluginsError("Error while loading: ${clazz.name} with ID '${it}', ${t.message}")
                }
            }
        }

        isRegistered = true

        plugins.values.forEach {
            TaskTimer.measure("Register plugin: ${it.details.id}") {
                it.registerInternal(Panels, context)
                it.log("Successfully registered plugin")
            }
        }
    }
}
