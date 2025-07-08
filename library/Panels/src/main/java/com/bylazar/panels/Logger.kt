package com.bylazar.panels

object Logger {
    private const val CONFIGURABLES_PREFIX = "Configurables"
    private const val SOCKET_PREFIX = "Socket"
    private const val SERVER_PREFIX = "Server"
    private const val PLUGINS_PREFIX = "Plugins"
    private const val CORE_PREFIX = "Core"

    private fun getCallerClassName(): String {
        return Throwable()
            .stackTrace
            .firstOrNull { !it.className.contains("com.bylazar.panels.Logger") }
            ?.className
            ?.substringAfterLast('.')
            ?: "Unknown"
    }

    fun log(prefix: String, message: String) {
        println("PANELS: ${prefix.uppercase()}: (${getCallerClassName()}): $message")
    }

    fun error(prefix: String, message: String) {
        println("PANELS: ${prefix.uppercase()}: (${getCallerClassName()}): ERROR: $message")
    }

    fun configurablesLog(message: String) = log(CONFIGURABLES_PREFIX, message)
    fun configurablesError(message: String) = error(CONFIGURABLES_PREFIX, message)

    fun socketLog(message: String) = log(SOCKET_PREFIX, message)
    fun socketError(message: String) = error(SOCKET_PREFIX, message)

    fun serverLog(message: String) = log(SERVER_PREFIX, message)
    fun serverError(message: String) = error(SERVER_PREFIX, message)

    fun pluginsLog(message: String) = log(PLUGINS_PREFIX, message)
    fun pluginsError(message: String) = error(PLUGINS_PREFIX, message)

    fun coreLog(message: String) = log(CORE_PREFIX, message)
    fun coreError(message: String) = error(CORE_PREFIX, message)
}