package com.bylazar.panels.json

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
    val includedPluginsIDs: List<String> = listOf(),
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
                appendLine("    PanelsWidget(name='${widget.name}', filepath='${widget.filepath}'),")
            }
            appendLine("  ],")

            appendLine("  navlets=[")
            navlets.forEach { navlet ->
                appendLine("    PanelsWidget(name='${navlet.name}', filepath='${navlet.filepath}'),")
            }
            appendLine("  ],")

            appendLine("  manager=PanelsWidget(name='${manager.name}', filepath='${manager.filepath}'),")

            appendLine("  docs=PanelsDocs(")
            appendLine("    homepage=PanelsWidget(name='${docs.homepage.name}', filepath='${docs.homepage.filepath}'),")
            appendLine("    chapters=[")
            docs.chapters.forEach { chapter ->
                appendLine("      PanelsWidget(name='${chapter.name}', filepath='${chapter.filepath}'),")
            }
            appendLine("    ]")
            appendLine("  )")

            append(")")
        }
    }
}

data class PanelsWidget(
    val name: String = "",
    val filepath: String = "",
    var textContent: String = ""
)

data class PanelsDocs(
    val homepage: PanelsWidget = PanelsWidget(),
    val chapters: List<PanelsWidget> = listOf()
)

data class Template(
    val name: String = "",
    val widgets: List<TemplateWidgetGroup> = listOf(),
    val navlets: List<TemplateNavlet> = listOf(),
    val plugins: List<String> = listOf()
)

data class TemplateWidget(
    val pluginID: String = "",
    val widgetID: String = ""
)

data class TemplateWidgetGroup(
    val widgets: List<TemplateWidget> = listOf(),
    val x: Int = 0,
    val y: Int = 0,
    val w: Int = 0,
    val h: Int = 0
)

data class TemplateNavlet(
    val pluginID: String = "",
    val navletID: String = ""
)
