package com.bylazar.configurables.variables

import com.bylazar.configurables.ConfigurablesLogger
import com.bylazar.configurables.GenericTypeJson
import com.bylazar.configurables.GlobalConfigurables
import com.bylazar.configurables.variables.generics.GenericVariable
import java.lang.reflect.Field
import java.lang.reflect.Type
import java.util.Locale
import java.util.UUID
import kotlin.text.*

class MyField constructor(
    val name: String,
    val type: Class<*>,
    val classLoader: ClassLoader,
    var isAccessible: Boolean,
    var get: (instance: Any?) -> Any?,
    var set: (instance: Any?, newValue: Any?) -> Unit,
    var genericType: Type? = null,
    val ref: Field? = null,
    val parentField: MyField? = null,
    override val className: String = ""
) : GenericVariable(className) {
    val myType = getType(type, this, parentField)
    val possibleValues = when (myType) {
        BaseTypes.BOOLEAN -> listOf("true", "false")
        BaseTypes.ENUM -> ref?.type?.enumConstants?.map { it.toString() }
        else -> null
    }
    val id = UUID.randomUUID().toString()

    init {
        if (ref != null) ref.isAccessible = true
        GlobalConfigurables.fieldsMap[id] = this
        GlobalConfigurables.loadedFieldsMap.getOrPut(classLoader, ::mutableListOf).add(id)
    }

    fun getValue(recursionDepth: Int = 0): Any? {
        if (recursionDepth > MAX_RECURSION_DEPTH) return null
        return try {
            get(parentField?.getValue(recursionDepth + 1))
        } catch (e: Exception) {
            ConfigurablesLogger.error("Error getting value for $name: ${e.message}")
            throw RuntimeException("PANELS: Could not get value for $name", e)
            null
        }
    }

    fun setValue(newValue: String): Boolean {
        return setValue(convertValue(newValue, myType, ref?.type?.enumConstants) ?: return false)
    }

    fun setValue(newValue: Any): Boolean {
        return try {
            set(parentField?.getValue(), newValue)
            true
        } catch (e: Exception) {
            ConfigurablesLogger.error("Error setting value for $name: ${e.message}")
            false
        }
    }

    override val toJsonType: GenericTypeJson
        get() {
            val rawValue = getValue()
            val value = when (rawValue) {
                is Float, is Double -> String.format(Locale.US, "%.10f", rawValue).trimEnd('0')
                    .trimEnd('.')

                else -> rawValue.toString()
            }
            return GenericTypeJson(
                id = id,
                className = className,
                fieldName = name,
                type = myType,
                value = value,
                possibleValues = possibleValues
            )
        }
}

fun convertToMyField(classLoader: ClassLoader, field: Field): MyField {
    return MyField(
        name = field.name,
        type = field.type,
        classLoader = classLoader,
        isAccessible = field.isAccessible,
        get = field::get,
        set = field::set,
        genericType = field.genericType,
        ref = field
    )
}

fun convertToMyField(field: MyField, parentField: MyField?, className: String): MyField {
    return MyField(
        name = field.name,
        type = field.type,
        classLoader = field.classLoader,
        isAccessible = field.isAccessible,
        get = field.get,
        set = field.set,
        genericType = field.genericType,
        ref = field.ref,
        parentField = parentField,
        className = className,
    )
}