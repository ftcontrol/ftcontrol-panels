package com.bylazar.telemetry

import org.firstinspires.ftc.robotcore.external.Func
import org.firstinspires.ftc.robotcore.external.Telemetry
import java.util.Locale

class TelemetryManager(
    val config: () -> TelemetryPluginConfig,
    private val sendLines: (lines: MutableList<String>) -> Unit
) {
    var lines = mutableListOf<String>()

    internal var lastLines = mutableListOf<String>()

    val updateInterval: Long
        get() = config().telemetryUpdateInterval
    var lastUpdate = 0L
    val timeSinceLastUpdate: Long
        get() = System.currentTimeMillis() - lastUpdate
    val shouldUpdateLines: Boolean
        get() = timeSinceLastUpdate >= updateInterval

    fun addData(key: String, value: Any) {
        lines.add("$key: $value")
    }

    fun addData(key: String, value: String) {
        lines.add("$key: $value")
    }

    fun addLine(line: String) {
        lines.add(line)
    }

    fun debug(vararg data: String) {
        data.forEach { lines.add(it) }
    }

    fun debug(vararg data: Any) {
        data.forEach { lines.add(it.toString()) }
    }

    fun update() {
        if (lines.isEmpty()) return
        if (shouldUpdateLines) {
            sendLines(lines)
            lastLines = lines
            lastUpdate = System.currentTimeMillis()
        }
        lines.clear()
    }

    fun update(telemetry: Telemetry) {
        lines.forEach { telemetry.addLine(it) }
        telemetry.update()
        update()
    }

    val wrapper = TelemetryWrapper()

    inner class TelemetryWrapper : Telemetry {
        private var itemSeparator: String = ", "
        private var captionValueSeparator: String = ": "

        override fun addData(caption: String?, format: String?, vararg args: Any?): Telemetry.Item? {
            val cap = caption ?: ""
            val fmt = format ?: "%s"
            val formatted = try {
                String.format(Locale.getDefault(), fmt, *args)
            } catch (_: Exception) {
                listOf(fmt, *args).joinToString(" ")
            }
            this@TelemetryManager.addData(cap, formatted)
            return null
        }

        override fun addData(caption: String?, value: Any?): Telemetry.Item? {
            val cap = caption ?: ""
            this@TelemetryManager.addData(cap, value?.toString() ?: "null")
            return null
        }

        override fun <T : Any?> addData(caption: String?, valueProducer: Func<T?>?): Telemetry.Item? {
            val cap = caption ?: ""
            this@TelemetryManager.addData(cap, valueProducer?.value()?.toString() ?: "null")
            return null
        }

        override fun <T : Any?> addData(caption: String?, format: String?, valueProducer: Func<T?>?): Telemetry.Item? {
            val cap = caption ?: ""
            val fmt = format ?: "%s"
            val produced = valueProducer?.value()
            val formatted = try {
                String.format(Locale.getDefault(), fmt, produced)
            } catch (_: Exception) {
                "$fmt $produced"
            }
            this@TelemetryManager.addData(cap, formatted)
            return null
        }

        override fun addLine(): Telemetry.Line? {
            this@TelemetryManager.addLine("")
            return null
        }

        override fun addLine(lineCaption: String?): Telemetry.Line? {
            val cap = lineCaption ?: ""
            this@TelemetryManager.addLine(cap)
            return null
        }

        override fun update(): Boolean {
            this@TelemetryManager.update()
            return true
        }

        override fun getItemSeparator(): String = itemSeparator
        override fun setItemSeparator(itemSeparator: String?) {
            this.itemSeparator = itemSeparator ?: ", "
        }

        override fun getCaptionValueSeparator(): String = captionValueSeparator
        override fun setCaptionValueSeparator(captionValueSeparator: String?) {
            this.captionValueSeparator = captionValueSeparator ?: ": "
        }

        override fun removeItem(item: Telemetry.Item?) = false
        override fun clear() = this@TelemetryManager.lines.clear()
        override fun clearAll() = this@TelemetryManager.lines.clear()
        override fun removeLine(line: Telemetry.Line?) = false
        override fun isAutoClear() = true
        override fun setAutoClear(autoClear: Boolean) {}
        override fun getMsTransmissionInterval() = updateInterval.toInt()
        override fun setMsTransmissionInterval(msTransmissionInterval: Int) {}
        override fun setDisplayFormat(displayFormat: Telemetry.DisplayFormat?) {}
        override fun log(): Telemetry.Log? = null
        override fun addAction(action: Runnable?): Any? = null
        override fun removeAction(token: Any?): Boolean = false
        override fun speak(text: String?) {}
        override fun speak(text: String?, languageCode: String?, countryCode: String?) {}
    }
}