package com.bylazar.configurables

import com.bylazar.configurables.variables.BaseTypes

data class ReceivedJvmFields(
    var fields: List<GenericTypeJson>
)


data class ReceivedInitialJvmFields(
    var fields: List<GenericTypeJson>
)


data class UpdatedJvmFields(
    var fields: List<ChangeJson>
)


class GenericTypeJson(
    val id: String,
    val className: String = "",
    val fieldName: String,
    val type: BaseTypes,
    val valueString: String,
    val newValueString: String,
    val value: String = "",
    val isValid: Boolean = true,
    val possibleValues: List<String>? = null,
    val customValues: List<GenericTypeJson>? = null,
    val isOpened: Boolean? = null
)

class ChangeJson(
    val id: String,
    val newValueString: String
)