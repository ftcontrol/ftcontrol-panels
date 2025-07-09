package com.bylazar.panels.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties



@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginDetails(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val version: String = "",
    val panelsVersion: String = "",
    val author: String = "",
    val widgets: List<PanelsWidget> = listOf()
){
    override fun toString(): String {
        return buildString {
            appendLine("PluginDetails(")
            appendLine("  id='$id',")
            appendLine("  name='$name',")
            appendLine("  description='$description',")
            appendLine("  version='$version',")
            appendLine("  panelsVersion='$panelsVersion',")
            appendLine("  author='$author',")
            appendLine("  widgets=[")
            widgets.forEach { widget ->
                appendLine("    PanelsWidget(name='${widget.name}', filepath='${widget.filepath}'),")
            }
            appendLine("  ]")
            append(")")
        }
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class PanelsWidget(
    val name: String,
    val filepath: String
)