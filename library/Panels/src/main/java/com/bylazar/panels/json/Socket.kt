package com.bylazar.panels.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

data class SocketMessage<T>(
    val pluginID: String,
    val messageID: String,
    val data: T
) {
    fun toJson(): String = mapper.writeValueAsString(this)

    companion object {
        val mapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule()

        inline fun <reified T> fromString(json: String): SocketMessage<T> {
            return mapper.readValue(json)
        }
    }
}

inline fun <reified T : Any> createSocketMessage(
    pluginID: String,
    messageID: String,
    payload: T
): SocketMessage<T> = SocketMessage(pluginID, messageID, payload)

data class TimeData(val time: String)
