package com.bylazar.configurables

import com.bylazar.configurables.variables.MyField
import com.bylazar.configurables.variables.generics.GenericField

object GlobalConfigurables {
    var jvmFields = mutableListOf<GenericField>()

    var fieldsMap = mutableMapOf<String, MyField>()
}