package com.bylazar.configurables.variables.instances

import com.bylazar.configurables.GenericTypeJson
import com.bylazar.configurables.variables.BaseTypes
import com.bylazar.configurables.variables.generics.GenericVariable

class RecursionReachedVariable(
    override val className: String,
    val name: String
) : GenericVariable(className) {
    override val toJsonType: GenericTypeJson
        get() = GenericTypeJson(
            id = "",
            className = className,
            fieldName = name,
            type = BaseTypes.RECURSION_REACHED,
            valueString = "",
            newValueString = "",
        )
}