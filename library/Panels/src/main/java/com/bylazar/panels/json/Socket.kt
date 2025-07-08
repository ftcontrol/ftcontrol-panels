package com.bylazar.panels.json

import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
sealed class SocketMessage {
    val pluginID: String = "core"
}

@Serializable
@SerialName("time")
data class TimeMessage(
    val time: String
) : SocketMessage()

val json = Json {
    serializersModule = SerializersModule {
        polymorphic(SocketMessage::class) {
            subclass(TimeMessage::class)
        }
    }
    encodeDefaults = false
    useArrayPolymorphism = false
    classDiscriminator = "kind"
}

inline fun <reified T : SocketMessage> T.toJson(): String {
    return json.encodeToString(PolymorphicSerializer(SocketMessage::class), this)
}