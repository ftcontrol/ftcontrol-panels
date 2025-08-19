package com.bylazar.graph

data class GraphEntry(
    val id: String,
    val value: String,
    val timestamp: Long
)

class GraphManager(
    val config: () -> GraphPluginConfig,
    private val sendGraph: (values: MutableList<GraphEntry>) -> Unit
) {
    val updateInterval: Long
        get() = config().graphUpdateInterval
    var lastUpdate = 0L
    val timeSinceLastUpdate: Long
        get() = System.currentTimeMillis() - lastUpdate
    val shouldUpdateLines: Boolean
        get() = timeSinceLastUpdate >= updateInterval

    val values = mutableListOf<GraphEntry>()

    fun addData(key: String, value: Number) {
        values.add(
            GraphEntry(
                id = key,
                value = value.toString(),
                timestamp = System.currentTimeMillis()
            )
        )
    }

    fun update() {
        if (values.isEmpty()) return
        if (shouldUpdateLines) {
            sendGraph(values)
            lastUpdate = System.currentTimeMillis()
        }
        values.clear()
    }
}