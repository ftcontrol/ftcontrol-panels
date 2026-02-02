package com.bylazar.configurables.variables


import com.bylazar.configurables.ConfigurablesLogger
import com.bylazar.configurables.GlobalConfigurables
import com.bylazar.configurables.annotations.IgnoreConfigurable
import com.bylazar.configurables.variables.generics.GenericField
import com.bylazar.configurables.variables.generics.GenericVariable
import com.bylazar.configurables.variables.instances.CustomVariable
import com.bylazar.configurables.variables.instances.ErrorVariable
import com.bylazar.configurables.variables.instances.RecursionReachedVariable
import com.bylazar.configurables.variables.instances.UnknownVariable
import com.bylazar.configurables.variables.instances.UnsupportedVariable
import com.qualcomm.robotcore.hardware.HardwareMap
import java.lang.reflect.Array
import kotlin.collections.get
import kotlin.jvm.java

const val MAX_RECURSION_DEPTH = 64

class RecursionDetectedException(val fieldName: String) : RuntimeException()

fun processValue(
    recursionDepth: Int,
    className: String,
    classLoader: ClassLoader,
    type: BaseTypes,
    reference: MyField,
    parentReference: MyField?,
    recursionItems: MutableList<String> = mutableListOf(),
): GenericVariable {
    try {
//        Logger.configurablesLog("Package ${reference.genericType?.javaClass?.packageName}")

        if (reference.type == HardwareMap::class.java) {
            return UnsupportedVariable(className, reference.name)
        }

        if (recursionItems.contains("this\$0") && reference.name == "this\$0") {
            ConfigurablesLogger.log("Detected self-reference or cycle: $recursionItems")
            throw RecursionDetectedException(reference.name)
        }

        recursionItems.add(reference.name)
        if (recursionDepth > MAX_RECURSION_DEPTH) {
            ConfigurablesLogger.log("Recursion depth exceeded: $recursionItems")
            return UnknownVariable(className, reference.name)
            throw RecursionDetectedException(reference.name)
        }
        val currentManager = convertToMyField(reference, parentReference, className)
        if (type in listOf(
                BaseTypes.INT, BaseTypes.DOUBLE, BaseTypes.FLOAT,
                BaseTypes.LONG, BaseTypes.STRING, BaseTypes.BOOLEAN, BaseTypes.ENUM
            )
        ) {
            return currentManager
        }

        if (type == BaseTypes.CUSTOM) {
            reference.isAccessible = true
            val customValues = reference.type.declaredFields.sortedBy { field ->
                field.getAnnotation(com.bylazar.configurables.annotations.Sorter::class.java)?.sort
                    ?: Int.MAX_VALUE
            }.mapNotNull { field ->
                try {
                    field.isAccessible = true
                    if (field.isAnnotationPresent(IgnoreConfigurable::class.java)) return@mapNotNull null

                    if (field.type == reference.type && field.name == reference.name) {
                        ConfigurablesLogger.log("Ignored inner instance of same type: ${field.name}")
                        return@mapNotNull null
                    }

//                if (field.type == reference.type){
//                    ConfigurablesManager.jvmFields.add(GenericField(reference.type.toString(), field))
//                    Logger.configurablesLog("Moved field to global configurable:${reference.name} ${reference.type} ${field.name}")
//                    return@mapNotNull null
//                }
                    if (field.type == reference.type) {
                        val newField = GenericField(classLoader, reference.type.toString(), field)
                        val alreadyExists = GlobalConfigurables.jvmFields.any { (_, fields) ->
                            fields.any {
                                it.className == newField.className && it.reference.name == newField.reference.name
                            }
                        }

                        if (!alreadyExists) {
                            GlobalConfigurables.jvmFields.getOrPut(classLoader, ::mutableListOf).add(newField)
                            ConfigurablesLogger.log("Moved field to global configurable: ${reference.name} ${reference.type} ${field.name}")
                        } else {
                            ConfigurablesLogger.log("Skipped duplicate configurable: ${reference.name} ${reference.type} ${field.name}")
                        }

                        return@mapNotNull null
                    }

                    val customNewField = convertToMyField(classLoader, field)

                    processValue(
                        recursionDepth + 1,
                        className,
                        classLoader,
                        getType(field.type, customNewField, reference),
                        customNewField,
                        currentManager,
                        recursionItems.toMutableList(),
                    )
                } catch (t: Throwable) {
                    null
                }
            }

            return CustomVariable(reference.name, className, customValues, BaseTypes.CUSTOM)
        }

        if (type == BaseTypes.ARRAY) {
            val componentType = reference.type.componentType
            val componentValues = currentManager.getValue()
            if (componentValues != null && componentType != null) {
                val arrayValues = (0 until Array.getLength(componentValues)).mapNotNull { i ->
                    try {
                        val value = Array.get(componentValues, i)

                        val arrayType = getType(componentType)

                        val itemType = if (arrayType == BaseTypes.UNKNOWN) {
                            getType(value.javaClass)
                        } else {
                            arrayType
                        }
                        val cType = if (arrayType == BaseTypes.UNKNOWN) {
                            value.javaClass
                        } else {
                            componentType
                        }

                        ConfigurablesLogger.log("Array type: $itemType")

                        val currentElementReference = MyField(
                            name = i.toString(),
                            type = cType,
                            classLoader = classLoader,
                            isAccessible = true,
                            get = { instance ->
                                Array.get(instance, i)
                            },
                            set = { instance, newValue ->
                                Array.set(instance, i, newValue)
                            },
                            genericType = componentType
                        )

                        processValue(
                            recursionDepth + 1,
                            className,
                            classLoader,
                            itemType,
                            currentElementReference,
                            currentManager,
                            recursionItems.toMutableList(),
                        )
                    } catch (t: Throwable) {
                        null
                    }
                }
                return CustomVariable(reference.name, className, arrayValues, BaseTypes.ARRAY)
            } else {
                return UnknownVariable(className, reference.name)
            }
        }

        if (type == BaseTypes.LIST) {
            val listInstance = currentManager.getValue() as? MutableList<Any?>
            if (listInstance != null) {

                val listValues = listInstance.mapIndexedNotNull { index, value ->
                    try {
                        val arrayType = getType(reference.type.componentType)

                        val itemType = if (arrayType == BaseTypes.UNKNOWN) {
                            getType(value?.javaClass ?: Any::class.java)
                        } else {
                            arrayType
                        }
                        val cType = if (arrayType == BaseTypes.UNKNOWN) {
                            value?.javaClass ?: Any::class.java
                        } else {
                            reference.type.componentType
                        }

                        ConfigurablesLogger.log("Array type: $itemType")

                        val currentElementReference = MyField(
                            name = index.toString(),
                            type = cType,
                            classLoader = classLoader,
                            isAccessible = true,
                            get = { instance ->
                                listInstance.getOrNull(index)
                            },
                            set = { instance, newValue ->
                                if (index in listInstance.indices) {
                                    listInstance[index] = newValue
                                } else {
                                    listInstance.add(newValue)
                                }
                            },
                            genericType = reference.type.componentType
                        )

                        processValue(
                            recursionDepth + 1,
                            className,
                            classLoader,
                            itemType,
                            currentElementReference,
                            currentManager,
                            recursionItems.toMutableList(),
                        )
                    } catch (t: Throwable) {
                        null
                    }
                }

                return CustomVariable(reference.name, className, listValues, BaseTypes.LIST)
            }
        }

        if (type == BaseTypes.MAP) {
            val mapInstance = currentManager.getValue() as? MutableMap<*, *>
            if (mapInstance != null) {
                ConfigurablesLogger.log("Map keys: ${mapInstance.map { (key, value) -> "$key.$value" }}")
                val mapValues: List<GenericVariable> = mapInstance.mapNotNull { (key, _) ->
                    try {
                        ConfigurablesLogger.log("Map key: $key")
                        val itemType = getType(mapInstance[key]?.javaClass)
                        val currentElementReference = MyField(
                            name = key.toString(),
                            type = mapInstance[key]?.javaClass ?: Any::class.java,
                            classLoader = classLoader,
                            isAccessible = true,
                            get = { instance ->
                                mapInstance[key]
                            },
                            set = { instance, newValue ->
                                (mapInstance as MutableMap<Any, Any>)[key as Any] = newValue as Any
                            },
                            genericType = reference.type.componentType
                        )

                        processValue(
                            recursionDepth + 1,
                            className,
                            classLoader,
                            itemType,
                            currentElementReference,
                            currentManager,
                            recursionItems.toMutableList(),
                        )
                    } catch (t: Throwable) {
                        null
                    }
                }

                ConfigurablesLogger.log("Map keys2: ${mapValues.map { it.toJsonType }}")

                return CustomVariable(reference.name, className, mapValues, BaseTypes.MAP)
            }
        }

        return UnknownVariable(className, reference.name)
    } catch (e: RecursionDetectedException) {
        if (recursionDepth == 0) {
            ConfigurablesLogger.error("Recursion detected and aborted at top-level.")
            return RecursionReachedVariable(className, reference.name)
        } else {
            ConfigurablesLogger.error("Other error: ${e.message}")
            return ErrorVariable(className, reference.name)
        }
    }
}