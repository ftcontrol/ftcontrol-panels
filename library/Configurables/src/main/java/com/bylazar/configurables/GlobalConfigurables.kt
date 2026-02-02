package com.bylazar.configurables

import com.bylazar.configurables.variables.MyField
import com.bylazar.configurables.variables.generics.GenericField

object GlobalConfigurables {
    var jvmFields = mutableMapOf<ClassLoader, MutableList<GenericField>>()
    var loadedFieldsMap = mutableMapOf<ClassLoader, MutableList<String>>()
    var fieldsMap = mutableMapOf<String, MyField>()
}