package com.bylazar.panels.json

import android.R.attr.x
import android.R.attr.y
import com.bylazar.panels.plugins.PluginsManager.plugins
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class PluginDetails(
    val id: String = "",
    val name: String = "",
    val letterName: String = "",
    val description: String = "",
    val websiteURL: String = "",
    val version: String = "",
    val pluginsCoreVersion: String = "",
    val author: String = "",
    val widgets: List<PanelsWidget> = listOf(),
    val navlets: List<PanelsWidget> = listOf(),
    val manager: PanelsWidget = PanelsWidget(),
    val docs: PanelsDocs = PanelsDocs(),
    val templates: List<Template> = listOf(),
) {
    override fun toString(): String {
        return buildString {
            appendLine("PluginDetails(")
            appendLine("  id='$id',")
            appendLine("  name='$name',")
            appendLine("  letterName='$letterName',")
            appendLine("  description='$description',")
            appendLine("  websiteURL='$websiteURL',")
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

@JsonIgnoreProperties(ignoreUnknown = true)
data class Template(
    val name: String = "",
    val widgets: List<TemplateWidgetGroup> = listOf(),
    val navlets: List<TemplateNavlet> = listOf(),
    val plugins: List<String> = listOf()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TemplateWidget(
    val pluginID: String = "",
    val widgetID: String = ""
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TemplateWidgetGroup(
    val widgets: List<TemplateWidget> = listOf(),
    val x: Int = 0,
    val y: Int = 0,
    val w: Int = 0,
    val h: Int = 0
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TemplateNavlet(
    val pluginID: String = "",
    val navletID: String = ""
)
