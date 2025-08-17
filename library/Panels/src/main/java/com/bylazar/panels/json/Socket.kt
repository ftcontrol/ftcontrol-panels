package com.bylazar.panels.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

data class SocketMessage(
    val pluginID: String,
    val messageID: String,
    val data: Any?
) {
    fun toJson(): String = gson.toJson(this)

    companion object {
        val gson: Gson = GsonBuilder().serializeSpecialFloatingPointValues().create()

        fun fromJson(json: String): SocketMessage {
            return gson.fromJson(json, SocketMessage::class.java)
        }

        inline fun <reified T> convertData(data: Any?): T? {
            if (data == null) return null
            val jsonString = gson.toJson(data)
            return gson.fromJson(jsonString, object : TypeToken<T>() {}.type)
        }
    }
}

data class TimeData(val time: String)

data class PluginData(
    var plugins: List<PluginInfo>,
    var skippedPlugins: List<PluginDetails>,
)

data class PluginInfo(
    var details: PluginDetails,
    var config: Any,
)