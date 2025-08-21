package com.bylazar.lights

import org.firstinspires.ftc.robotcore.external.Telemetry
import java.util.Locale

enum class LightType {
    RGB_INDICATOR,
    HEADLIGHT
}

enum class LightUpdateMode {
    MANUAL,
    PROVIDER
}

data class LightObject(
    val id: String,
    val type: LightType,
    val value: Any
)

abstract class Light<T : Any>(open val id: String, val default: T) {
    abstract val type: LightType
    var updateMode = LightUpdateMode.MANUAL
    var updateProvider = { default }

    var value = default
        private set

    fun update(v: T = default) {
        value = when (updateMode) {
            LightUpdateMode.MANUAL -> v
            LightUpdateMode.PROVIDER -> updateProvider()
        }
    }

    fun reset() {
        value = default
    }

    fun withProvider(p: () -> T): Light<T> = apply {
        updateProvider = p
        updateMode = LightUpdateMode.PROVIDER
    }

    fun withManual(d: T = default): Light<T> = apply {
        updateProvider = { default }
        updateMode = LightUpdateMode.MANUAL
        value = d
    }

    val asObject: LightObject
        get() = LightObject(id, type, value)
}

class RGBIndicator(override val id: String) : Light<Double>(id, 0.0) {
    override val type = LightType.RGB_INDICATOR

    companion object {
        val OFF = 0.0 //000000
        val RED = 0.2777 //ff0300
        val ORANGE = 0.333 //ff9c00
        val YELLOW = 0.388 //fdfd00
        val SAGE = 0.444 //6db600
        val GREEN = 0.5 //007f0b
        val AZURE = 0.555 //007582
        val BLUE = 0.611 //006bfe
        val INDIGO = 0.666 //3c02ff
        val VIOLET = 0.722 //7500ff
        val WHITE = 1.0 //ffffff
    }
}

class Headlight(override val id: String) : Light<Boolean>(id, false) {
    override val type = LightType.HEADLIGHT

    companion object {
        val OFF = false
        val ON = true
    }
}

class LightsManager(
    val config: () -> LightsPluginConfig,
    private val sendLights: (lightsState: List<LightObject>) -> Unit
) {
    var lights = listOf<Light<*>>()

    val lightsState: List<LightObject>
        get() = lights.map { it.asObject }.sortedBy { it.type }.sortedBy { it.id }

    var lastLights = lightsState

    val updateInterval: Long
        get() = config().lightsUpdateInterval
    var lastUpdate = 0L
    val timeSinceLastUpdate: Long
        get() = System.currentTimeMillis() - lastUpdate
    val shouldUpdateLines: Boolean
        get() = timeSinceLastUpdate >= updateInterval

    fun initLights(l: List<Light<*>>) {
        sendLights(listOf<LightObject>())
        lights = l
    }
    fun initLights(vararg l: Light<*>) = initLights(l.toList())

    fun update() {
        if (lightsState.isEmpty()) return
        if (shouldUpdateLines) {
            sendLights(lightsState)
            lastLights = lightsState
            lastUpdate = System.currentTimeMillis()
        }
    }

    fun addToTelemetry(telemetry: Telemetry) {
        lights.forEach { light ->
            val v = light.value
            val displayValue = when (v) {
                is Double -> String.format(Locale.US, "%.2f", v)
                else -> v.toString()
            }
            telemetry.addLine("${light.id}: $displayValue")
        }
    }
}