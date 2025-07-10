package com.bylazar.configurables.variables.generics

import com.bylazar.configurables.GenericTypeJson

abstract class GenericVariable(
    open val className: String,
) {
    abstract val toJsonType: GenericTypeJson
}