package com.bylazar.configurables.variables.instances

import com.bylazar.configurables.GenericTypeJson
import com.bylazar.configurables.variables.BaseTypes
import com.bylazar.configurables.variables.generics.GenericVariable
import java.util.UUID

class CustomVariable(
    val fieldName: String,
    override val className: String,
    val values: List<GenericVariable>,
    val type: BaseTypes = BaseTypes.CUSTOM
) : GenericVariable(className) {

    val id = UUID.randomUUID().toString()

    override val toJsonType: GenericTypeJson
        get() {
            val valuesList: MutableList<GenericTypeJson> = mutableListOf()
            values.forEach {
                try{
                    valuesList.add(it.toJsonType)
                }catch (t: Throwable){
                    //skip
                }
            }
            return GenericTypeJson(
                id = id,
                className = className,
                fieldName = fieldName,
                type = type,
                value = "",
                customValues = valuesList
            )
        }

}