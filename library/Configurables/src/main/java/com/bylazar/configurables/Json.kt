package com.bylazar.configurables

import com.bylazar.configurables.variables.BaseTypes

class GenericTypeJson(
    var id: String,
    var className: String = "",
    var fieldName: String,
    var type: BaseTypes,
    var valueString: String,
    var newValueString: String,
    var value: String = "",
    var isValid: Boolean = true,
    var possibleValues: List<String>? = null,
    var customValues: List<GenericTypeJson>? = null,
    var isOpened: Boolean? = null
)

class ChangeJson(
    var id: String,
    var newValueString: String
)