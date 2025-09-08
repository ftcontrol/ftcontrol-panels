package com.bylazar.field

import java.io.FileNotFoundException
import android.util.Base64

data class FieldImage(
    val LIGHT: ImagePreset,
    val DARK: ImagePreset
)

fun interface ImagePreset {
    fun get(): String
}


object FieldImages {
    fun loadResourceAsBytes(resourceName: String): ByteArray {
        return this::class.java.classLoader?.getResource(resourceName)
            ?.openStream()
            ?.readBytes()
            ?: throw FileNotFoundException("Resource not found: $resourceName")
    }

    fun loadResourceAsBase64(resourceName: String): String {
        val bytes = loadResourceAsBytes(resourceName)
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    val DECODE = FieldImage(
        DARK = ImagePreset { loadResourceAsBase64("decode-dark.png") },
        LIGHT = ImagePreset { loadResourceAsBase64("decode-light.png") }
    )
}