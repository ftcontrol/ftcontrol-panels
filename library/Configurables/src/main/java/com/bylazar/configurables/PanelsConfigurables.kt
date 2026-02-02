package com.bylazar.configurables

import kotlin.reflect.KClass

object PanelsConfigurables {
    @Deprecated(
        "this function is misleading, instead, use the variants that take a class",
        replaceWith = ReplaceWith("refreshClass(cls::class)")
    )
    @JvmStatic
    fun refreshClass(cls: Any) = refreshClass(cls.javaClass)
    @JvmStatic
    fun refreshClass(cls: KClass<*>) = Plugin.refreshClass(cls.java)
    @JvmStatic
    fun refreshClass(cls: Class<*>) = Plugin.refreshClass(cls)
}