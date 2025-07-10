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

    inline fun <reified T> dataAs(): T = mapper.convertValue(data, T::class.java)

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule()

        fun fromJson(json: String): SocketMessage {
            return mapper.readValue(json)
        }
    }
}

data class TimeData(val time: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginData(
    var plugins: List<PluginInfo>,
    var skippedPlugins: List<PluginDetails>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginInfo(
    var details: PluginDetails,
    var config: Any,
)