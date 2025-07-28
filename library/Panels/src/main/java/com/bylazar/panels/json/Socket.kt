package com.bylazar.panels.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

@JsonIgnoreProperties(ignoreUnknown = true)
data class SocketMessage(
    val pluginID: String,
    val messageID: String,
    val data: Any?
) {
    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule()

        fun fromJson(json: String): SocketMessage {
            return mapper.readValue(json)
        }

        inline fun <reified T> convertData(data: Any?): T {
            return mapper.convertValue(data, object : com.fasterxml.jackson.core.type.TypeReference<T>() {})
        }
    }
}

data class TimeData(val time: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginData(
    var plugins: List<PluginInfo>,
    var skippedPlugins: List<PluginDetails>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginInfo(
    var details: PluginDetails,
    var config: Any,
)