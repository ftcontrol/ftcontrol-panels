package com.bylazar.panels.plugins

import android.content.Context
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.reflection.ClassFinder
import kotlin.collections.set
import kotlin.jvm.java

object PluginsManager {
    val plugins: MutableMap<String, Plugin<*>> = mutableMapOf()

    fun init(context: Context){
        val classes = ClassFinder().findClasses(
            apkPath = context.packageCodePath,
            shouldKeepFilter = { clazz ->
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
            val configs = ClassFinder().findClasses(
                apkPath = context.packageCodePath,
                shouldKeepFilter = { clazz ->
                    pluginInstance.config::class.java.isAssignableFrom(clazz)  && clazz != pluginInstance.config::class.java
                }
            )

            Logger.pluginsLog("Found ${configs.size} configs.")

            if(configs.isNotEmpty()){
                val newConfig = clazz.getDeclaredConstructor().newInstance()
                pluginInstance.setConfig(newConfig)
                Logger.pluginsLog("Set new config.")
            }
            //

            plugins[uniqueId] = pluginInstance

            Logger.pluginsLog("Successfully registered plugin: ${clazz.name} with ID '$uniqueId'")

        }

        plugins.values.forEach {
            it.onRegister(Panels, context)
            it.log("Successfully registered plugin")
        }
    }
}