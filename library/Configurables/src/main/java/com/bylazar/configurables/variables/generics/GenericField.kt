package com.bylazar.configurables.variables.generics

import com.bylazar.configurables.ConfigurablesLogger
import com.bylazar.configurables.GenericTypeJson
import com.bylazar.configurables.variables.BaseTypes
import com.bylazar.configurables.variables.convertToMyField
import com.bylazar.configurables.variables.getType
import com.bylazar.configurables.variables.instances.JSONErrorVariable
import com.bylazar.configurables.variables.processValue
import java.lang.reflect.Field

class GenericField(
    classLoader: ClassLoader,
    var className: String,
    var reference: Field,
) {

    val type = getType(reference.type, convertToMyField(classLoader, reference), null)
    val value: GenericVariable = processValue(0, className, classLoader, type, convertToMyField(classLoader, reference), null)

    val isNull: Boolean
        get() = toJsonType.type == BaseTypes.JSON_ERROR

    val name: String
        get() = reference.name

    fun debug() {
        ConfigurablesLogger.log("Debug for $className / ${reference.name}")
        ConfigurablesLogger.log("Of type $type")
    }

    val toJsonType: GenericTypeJson
        get() {
            try {
                val json = value.toJsonType
                return json
            } catch (t: Throwable) {
                ConfigurablesLogger.error("Error getting JSON for $className / ${reference.name}: ${t.message}")
                return JSONErrorVariable(className, reference.name).toJsonType
            }
        }
}