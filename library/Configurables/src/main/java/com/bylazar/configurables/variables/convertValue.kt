package com.bylazar.configurables.variables

import com.bylazar.configurables.ConfigurablesLogger


fun convertValue(value: String, type: BaseTypes, enumConstants: Array<out Any>?): Any? {
    return when (type) {
        BaseTypes.INT -> {
            when {
                value.toIntOrNull() != null -> value.toInt()
                value.toFloatOrNull() != null -> value.toFloat()
                    .toInt()

                value.toDoubleOrNull() != null -> value.toDouble()
                    .toInt()

                else -> value.toInt()
            }
        }

        BaseTypes.DOUBLE -> {
            when {
                value.toDoubleOrNull() != null -> value.toDouble()
                value.toFloatOrNull() != null -> value.toFloat()
                    .toDouble()

                else -> value.toDouble()
            }
        }

        BaseTypes.STRING -> {
            value
        }

        BaseTypes.BOOLEAN -> {
            value.toBoolean()
        }

        BaseTypes.FLOAT -> {
            when {
                value.toFloatOrNull() != null -> value.toFloat()
                value.toDoubleOrNull() != null -> value.toDouble()
                    .toFloat()

                else -> value.toFloat()
            }
        }

        BaseTypes.LONG -> {
            when {
                value.toLongOrNull() != null -> value.toLong()
                value.toDoubleOrNull() != null -> value.toDouble()
                    .toLong()

                else -> value.toLong()
            }
        }

        BaseTypes.ENUM -> {
            if (enumConstants == null) return null
            try {
                val enumType = enumConstants.first()::class.java
                return enumType.enumConstants.find { it.toString() == value }
            } catch (e: Exception) {
                ConfigurablesLogger.error("Error converting to enum: ${e.message}")
                return null
            }
        }

        else -> null
    }
}