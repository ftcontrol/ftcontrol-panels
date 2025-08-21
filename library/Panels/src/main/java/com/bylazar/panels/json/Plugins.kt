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
    val components: List<PanelsWidget> = listOf(),
    val manager: String = "",
    val templates: List<Template> = listOf(),
    val includedPluginsIDs: List<String> = listOf(),
    val changelog: List<ChangeLogEntry> = listOf()
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
            components.forEach { c ->
                appendLine("    PanelsWidget(type='${c.type}', name='${c.id}', filepath='${c.filepath}'),")
            }
            appendLine("  ],")

            appendLine("  manager=PanelsWidget(filepath='${manager}'),")

            append(")")
        }
    }
}

data class PanelsWidget(
    val type: String = "",
    val id: String = "",
    val filepath: String = "",
)

data class Template(
    val name: String = "",
    val widgets: List<TemplateWidgetGroup> = listOf(),
    val navlets: List<TemplateNavlet> = listOf(),
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
data class ChangeLogEntry(
    val version: String = "",
    val releaseDate: String = "",
    val changes: List<Change> = listOf()
)

data class Change(
    val type: ChangeLogType = ChangeLogType.OTHER,
    val description: String = "",
    val upgrading: String = ""
)

enum class ChangeLogType {
    ADDED,
    CHANGED,
    DEPRECATED,
    REMOVED,
    FIXED,
    DOCS,
    OTHER
}