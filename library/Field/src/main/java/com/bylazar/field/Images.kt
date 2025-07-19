package com.bylazar.field

data class FieldImage(
    val LIGHT: ImagePreset,
    val DARK: ImagePreset
)

fun interface ImagePreset {
    fun get(): String
}

class FieldImages {
    val INTO_THE_DEEP = FieldImage(
        LIGHT = ImagePreset { INTO_THE_DEEP_DARK },
        DARK = ImagePreset { INTO_THE_DEEP_LIGHT }
    )
}