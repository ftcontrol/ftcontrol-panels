package com.bylazar.telemetry

import org.firstinspires.ftc.robotcore.external.Func
import org.firstinspires.ftc.robotcore.external.Telemetry
import java.util.Locale

class JoinedTelemetry(private vararg val delegates: Telemetry) : Telemetry {

    private var localItemSeparator: String = ", "
    private var localCaptionValueSeparator: String = ": "

    override fun addData(caption: String?, format: String?, vararg args: Any?): Telemetry.Item? {
        val cap = caption ?: ""
        val fmt = format ?: "%s"
        val formatted = try {
            String.format(Locale.getDefault(), fmt, *args)
        } catch (_: Exception) {
            listOf(fmt, *args).joinToString(" ")
        }
        delegates.forEach { it.addData(cap, formatted) }
        return null
    }

    override fun addData(caption: String?, value: Any?): Telemetry.Item? {
        val cap = caption ?: ""
        val v = value?.toString() ?: "null"
        delegates.forEach { it.addData(cap, v) }
        return null
    }

    override fun <T : Any?> addData(caption: String?, valueProducer: Func<T?>?): Telemetry.Item? {
        val cap = caption ?: ""
        val v = valueProducer?.value()?.toString() ?: "null"
        delegates.forEach { it.addData(cap, v) }
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
        delegates.forEach { it.addData(cap, formatted) }
        return null
    }

    override fun update(): Boolean {
        var any = false
        delegates.forEach { if (it.update()) any = true }
        return any
    }


    override fun getItemSeparator(): String {
        return delegates.firstOrNull()?.itemSeparator ?: localItemSeparator
    }

    override fun setItemSeparator(itemSeparator: String?) {
        val sep = itemSeparator ?: ", "
        localItemSeparator = sep
        delegates.forEach { it.itemSeparator = sep }
    }

    override fun getCaptionValueSeparator(): String {
        return delegates.firstOrNull()?.captionValueSeparator ?: localCaptionValueSeparator
    }

    override fun setCaptionValueSeparator(captionValueSeparator: String?) {
        val sep = captionValueSeparator ?: ": "
        localCaptionValueSeparator = sep
        delegates.forEach { it.captionValueSeparator = sep }
    }


    override fun removeItem(item: Telemetry.Item?) = false
    override fun clear() { delegates.forEach { it.clear() } }
    override fun clearAll() { delegates.forEach { it.clearAll() } }
    override fun addLine(): Telemetry.Line? { delegates.forEach { it.addLine() }; return null }
    override fun addLine(lineCaption: String?): Telemetry.Line? { delegates.forEach { it.addLine(lineCaption) }; return null }
    override fun removeLine(line: Telemetry.Line?) = false
    override fun isAutoClear(): Boolean = true
    override fun setAutoClear(autoClear: Boolean) { delegates.forEach { it.isAutoClear = autoClear } }
    override fun getMsTransmissionInterval(): Int = delegates.firstOrNull()?.msTransmissionInterval ?: 0
    override fun setMsTransmissionInterval(msTransmissionInterval: Int) { delegates.forEach { it.msTransmissionInterval = msTransmissionInterval } }
    override fun setDisplayFormat(displayFormat: Telemetry.DisplayFormat?) { delegates.forEach { it.setDisplayFormat(displayFormat) } }
    override fun log(): Telemetry.Log? = null
    override fun addAction(action: Runnable?): Any? = null
    override fun removeAction(token: Any?): Boolean = false
    override fun speak(text: String?) {}
    override fun speak(text: String?, languageCode: String?, countryCode: String?) {}
}