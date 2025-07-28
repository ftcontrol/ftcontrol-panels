package com.bylazar.panels.plugins

import android.content.Context
import com.bylazar.panels.GlobalStats
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.json.PluginDetails
import com.bylazar.panels.reflection.ClassFinder
import com.bylazar.panels.reflection.ClassFinder.apkPath
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
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
            Logger.pluginsLog("JSON is $raw")
            val mapper = jacksonObjectMapper()
            mapper.registerKotlinModule()
            return mapper.readValue(raw, PluginDetails::class.java)
        }
    }

    fun loadPluginWidgetFile(context: Context, id: String, file: String): String {
        val assetManager = context.assets
        assetManager.open("web/plugins/${id}/widgets/${file}.js").use { inputStream ->
            return inputStream.bufferedReader().use { it.readText() }
        }
    }

    fun loadPluginNavletFile(context: Context, id: String, file: String): String {
        val assetManager = context.assets
        assetManager.open("web/plugins/${id}/navlets/${file}.js").use { inputStream ->
            return inputStream.bufferedReader().use { it.readText() }
        }
    }

    fun loadPluginDocsFile(context: Context, id: String, file: String): String {
        val assetManager = context.assets
        assetManager.open("web/plugins/${id}/docs/${file}.js").use { inputStream ->
            return inputStream.bufferedReader().use { it.readText() }
        }
    }

    fun loadPluginManagerFile(context: Context, id: String, file: String): String {
        val assetManager = context.assets
        assetManager.open("web/plugins/${id}/${file}.js").use { inputStream ->
            return inputStream.bufferedReader().use { it.readText() }
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

            val originalId = pluginInstance.id
            var uniqueId = originalId
            var suffix = 1

            while (plugins.containsKey(uniqueId)) {
                Logger.pluginsLog("Plugin ID '$originalId' is already registered. Renaming...")
                uniqueId = "$originalId${suffix++}"
            }

            pluginInstance.id = uniqueId

            Logger.pluginsLog("Got ${pluginInstance.id}.")

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
            //

            try {
                val details: PluginDetails =
                    loadPluginConfig(context, "web/plugins/${pluginInstance.id}/config.json")

                pluginInstance.details = details

                Logger.pluginsLog(pluginInstance.details.toString())

                //get all widgets content

                pluginInstance.details.widgets.forEach {
                    it.textContent =
                        loadPluginWidgetFile(context, pluginInstance.details.id, it.name)
                }

                pluginInstance.details.navlets.forEach {
                    it.textContent =
                        loadPluginNavletFile(context, pluginInstance.details.id, it.name)
                }

                pluginInstance.details.manager.textContent = loadPluginManagerFile(
                    context,
                    pluginInstance.details.id,
                    pluginInstance.details.manager.name
                )

                pluginInstance.details.docs.chapters.forEach {
                    it.textContent =
                        loadPluginDocsFile(context, pluginInstance.details.id, it.name)
                }

                pluginInstance.details.docs.homepage.textContent = loadPluginDocsFile(
                    context,
                    pluginInstance.details.id,
                    pluginInstance.details.docs.homepage.name
                )

                Logger.pluginsLog("Got details or ID '$uniqueId'")

                if (pluginInstance.details.pluginsCoreVersion != GlobalStats.pluginsCoreVersion) {
                    skippedPlugins[pluginInstance.id] = pluginInstance.details
                    Logger.pluginsLog("Skipped plugin: ${clazz.name} with ID '$uniqueId', coreVersion: ${pluginInstance.details.pluginsCoreVersion}, latest: ${GlobalStats.pluginsCoreVersion}")
                } else {
                    plugins[uniqueId] = pluginInstance
                    Logger.pluginsLog("Successfully registered plugin: ${clazz.name} with ID '$uniqueId'")
                }

            } catch (t: Throwable) {
                Logger.pluginsError("Error while loading: ${clazz.name} with ID '$uniqueId', ${t.message}")
            }
        }

        isRegistered = true

        plugins.values.forEach {
            it.registerInternal(Panels, context)
            it.log("Successfully registered plugin")
        }
    }
}