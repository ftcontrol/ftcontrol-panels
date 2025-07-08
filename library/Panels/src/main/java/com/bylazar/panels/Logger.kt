package com.bylazar.panels

object Logger {
    const val REFLECTION_PREFIX = "Reflection"
    const val SOCKET_PREFIX = "Socket"
    const val SERVER_PREFIX = "Server"
    const val PLUGINS_PREFIX = "Plugins"
    const val CORE_PREFIX = "Core"

    private fun getCallerClassName(): String {
        if(!Panels.config.enableClassCallerLogs) return "Disabled"
        return Throwable()
            .stackTrace
            .firstOrNull { !it.className.contains("com.bylazar.panels.Logger") }
            ?.className
            ?.substringAfterLast('.')
            ?: "Unknown"
    }

    fun log(prefix: String, message: String) {
        if(!Panels.config.enableLogs) return
        println("PANELS: ${prefix.uppercase()}: (${getCallerClassName()}): $message")
    }

    fun error(prefix: String, message: String) {
        if(!Panels.config.enableLogs) return
        println("PANELS: ${prefix.uppercase()}: (${getCallerClassName()}): ERROR: $message")
    }

    fun reflectionLog(message: String) = log(REFLECTION_PREFIX, message)
    fun reflectionError(message: String) = error(REFLECTION_PREFIX, message)

    fun socketLog(message: String) = log(SOCKET_PREFIX, message)
    fun socketError(message: String) = error(SOCKET_PREFIX, message)

    fun serverLog(message: String) = log(SERVER_PREFIX, message)
    fun serverError(message: String) = error(SERVER_PREFIX, message)

    fun pluginsLog(message: String) = log(PLUGINS_PREFIX, message)
    fun pluginsError(message: String) = error(PLUGINS_PREFIX, message)

    fun coreLog(message: String) = log(CORE_PREFIX, message)
    fun coreError(message: String) = error(CORE_PREFIX, message)
}