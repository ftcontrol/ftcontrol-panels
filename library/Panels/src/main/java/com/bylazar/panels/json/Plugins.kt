package com.bylazar.panels.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginDetails(
    val id: String = "",
    val name: String = "",
    val letterName: String = "",
    val description: String = "",
    val websiteURL: String = "",
    val devURL: String = "",
    val version: String = "",
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
            appendLine("  letterName='$letterName',")
            appendLine("  description='$description',")
            appendLine("  websiteURL='$websiteURL',")
            appendLine("  devURL='$devURL',")
            appendLine("  version='$version',")
            appendLine("  pluginsCoreVersion='$pluginsCoreVersion',")
            appendLine("  author='$author',")

            appendLine("  widgets=[")
            widgets.forEach { widget ->
                appendLine("    PanelsWidget(name='${widget.name}', filepath='${widget.filepath}', textContent='${widget.textContent}'),")
            }
            appendLine("  ],")

            appendLine("  navlets=[")
            navlets.forEach { navlet ->
                appendLine("    PanelsWidget(name='${navlet.name}', filepath='${navlet.filepath}', textContent='${navlet.textContent}'),")
            }
            appendLine("  ],")

            appendLine("  manager=PanelsWidget(name='${manager.name}', filepath='${manager.filepath}', textContent='${manager.textContent}'),")

            appendLine("  docs=PanelsDocs(")
            appendLine("    homepage=PanelsWidget(name='${docs.homepage.name}', filepath='${docs.homepage.filepath}', textContent='${docs.homepage.textContent}'),")
            appendLine("    chapters=[")
            docs.chapters.forEach { chapter ->
                appendLine("      PanelsWidget(name='${chapter.name}', filepath='${chapter.filepath}', textContent='${chapter.textContent}'),")
            }
            appendLine("    ]")
            appendLine("  )")

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