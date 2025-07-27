package com.bylazar.panels.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginDetails(
    val id: String = "",
    val name: String = "",
    val letterName: String = "",
    val description: String = "",
    val version: String = "",
    val panelsVersion: String = "",
    val pluginsCoreVersion: String = "",
    val author: String = "",
    val widgets: List<PanelsWidget> = listOf(),
    val navlets: List<PanelsWidget> = listOf(),
    val manager: PanelsWidget = PanelsWidget(),
    val docs: PanelsDocs = PanelsDocs()
) {
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
    val name: String = "",
    val filepath: String = "",
    var textContent: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PanelsDocs(
    val homepage: PanelsWidget = PanelsWidget(),
    val chapters: List<PanelsWidget> = listOf()
)