package com.bylazar.panels.server

import android.content.Context
import android.content.res.AssetManager
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.TaskTimer
import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.plugins.PluginsManager
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import java.lang.ref.WeakReference
import java.security.MessageDigest

class StaticServer(
    context: Context,
    port: Int,
    private val baseFolder: String
) : NanoHTTPD(port) {
    private val contextRef = WeakReference(context)

    private val assetManager: AssetManager?
        get() = contextRef.get()?.assets

    init {
        var files = listWebFiles(baseFolder)

        Logger.serverLog("Found ${files.size} web files")
        files.forEach {
            Logger.serverLog("Found file: $it")
        }
    }

    private fun listWebFiles(path: String): List<String> {
        val assets = assetManager
        if (assets == null) {
            Logger.serverLog("Context lost, cannot list files.")
            return mutableListOf()
        }

        val fileList = mutableListOf<String>()

        fun listFilesRecursive(currentPath: String) {
            try {
                val files = assets.list(currentPath) ?: return
                for (file in files) {
                    val fullPath = if (currentPath.isEmpty()) file else "$currentPath/$file"
                    val subFiles = assets.list(fullPath)
                    if (subFiles == null || subFiles.isEmpty()) {
                        fileList.add(fullPath)
                    } else {
                        listFilesRecursive(fullPath)
                    }
                }
            } catch (e: Exception) {
                Logger.serverLog("Error listing files at $currentPath: ${e.message}")
            }
        }

        listFilesRecursive(path)
        return fileList
    }

    private fun Response.allowCors(): Response {
        addHeader("Access-Control-Allow-Origin", "*")
        addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        addHeader("Access-Control-Allow-Headers", "*")
        return this
    }

    private fun Response.gzip(): Response {
        addHeader("Content-Encoding", "gzip")
        return this
    }

    fun getResponse(
        content: String,
        contentType: String = "text/html",
        status: Response.Status = Response.Status.OK
    ): Response {
        return newFixedLengthResponse(
            status,
            contentType,
            content
        ).allowCors()
    }

    fun ByteArray.sha256Hex(): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(this)
            .joinToString("") { "%02x".format(it) }
    }

    fun String.sha256Hex(): String {
        return this.toByteArray().sha256Hex()
    }

    private inline fun <T> List<T>.toJsonArray(crossinline render: (T) -> String): String =
        this.joinToString(separator = ",", prefix = "[", postfix = "]") { render(it) }

    var response = "null"
    var lastSha = "null"

    val pluginSvelteSha256 = mutableMapOf<String, String>()

    fun prepareData() {
        TaskTimer.start("building plugin info")

        val assets = assetManager
        if (assets != null) {
            pluginSvelteSha256.clear()
            for (id in PluginsManager.plugins.keys) {
                try {
                    assets.open("web/plugins/${id}/svelte.js").use { input ->
                        val bytes = input.readBytes()
                        pluginSvelteSha256[id] = bytes.sha256Hex()
                    }
                } catch (e: Exception) {
                    Logger.serverLog("No svelte.js for plugin $id")
                }
            }
        }

        val pluginsString = PluginsManager.plugins.entries
            .sortedBy { it.key }
            .map { it.value.toInfo() }
            .toJsonArray { it }

        val skippedPluginsString = PluginsManager.skippedPluginsStrings.entries
            .sortedBy { it.key }
            .map { it.value }
            .toJsonArray { it }

        val sb = StringBuilder(256 + pluginsString.length + skippedPluginsString.length)
        sb.append("{\"messageID\":\"pluginsDetails\",\"pluginID\":\"core\",\"data\":{")
            .append("\"plugins\":").append(pluginsString).append(',')
            .append("\"skippedPlugins\":").append(skippedPluginsString)
            .append("}}")

        response = sb.toString()
        lastSha = response.sha256Hex()

        Logger.serverLog("Sending ${response.length} characters (~${response.length / 1024} KB)")
        TaskTimer.end("building plugin info")
    }

    override fun serve(session: IHTTPSession): Response {
        if (!Panels.wasStarted) {
            return getResponse("Panels has not started yet.").allowCors()
        }

        if (session.method == Method.OPTIONS) {
            return getResponse("")
        }

        val uri = session.uri.removePrefix("/").removeSuffix("/").removePrefix("index.html")
            .ifEmpty { "index.html" }

        if (uri == "api/plugins") {
            return getResponse(
                response,
                "text/html"
            ).allowCors()
        }
        if (uri == "api/sha256") {
            return getResponse(lastSha).allowCors()
        }
        if (uri == "api/performance") {
            return getResponse(
                SocketMessage(
                    "core",
                    "performanceReadings",
                    TaskTimer.records
                ).toJson(),
                "application/json"
            ).allowCors()
        }

        if (uri == "api/shas") {
            val json = buildString {
                append('{')
                var first = true
                for (id in PluginsManager.plugins.keys) {
                    if (!first) append(',')
                    append('"')
                        .append(id.replace("\"", "\\\""))
                        .append('"')
                        .append(':')
                    val sha = pluginSvelteSha256[id]
                    if (sha != null) {
                        append('"')
                            .append(sha.replace("\"", "\\\""))
                            .append('"')
                    } else {
                        append("null")
                    }
                    first = false
                }
                append('}')
            }

            return newFixedLengthResponse(
                Response.Status.OK,
                "application/json",
                json
            ).allowCors()
        }

        for (id in PluginsManager.plugins.keys) {
            if (uri == "api/svelte/${id}") {
                val assets = assetManager
                if (assets != null) {
                    val inputStream = assets.open("web/plugins/${id}/svelte.js")
                    return newChunkedResponse(
                        Response.Status.OK,
                        "application/javascript",
                        inputStream
                    ).allowCors().gzip()
                }
            }
            if (uri == "api/svelte/${id}/sha256") {
                val sha = pluginSvelteSha256[id]
                if (sha != null) {
                    return newFixedLengthResponse(
                        Response.Status.OK,
                        "text/plain",
                        sha
                    ).allowCors()
                } else {
                    return newFixedLengthResponse(
                        Response.Status.NOT_FOUND,
                        "text/plain",
                        "sha256 not found for $id"
                    ).allowCors()
                }
            }
            if (uri == "api/configs/${id}") {
                val assets = assetManager
                if (assets != null) {
                    val inputStream = assets.open("web/plugins/${id}/config.json")
                    return newChunkedResponse(
                        Response.Status.OK,
                        "application/json",
                        inputStream
                    ).allowCors()
                }
            }
        }

        return getStaticResponse(uri)
    }

    private fun getMimeType(extension: String): String = when (extension.lowercase()) {
        "css" -> "text/css"
        "htm", "html" -> "text/html"
        "js" -> "application/javascript"
        "json" -> "application/json"
        "png" -> "image/png"
        "jpg", "jpeg" -> "image/jpeg"
        "svg" -> "image/svg+xml"
        else -> "text/html"
    }

    private fun getStaticResponse(uri: String): Response {
        val assets = assetManager
        if (assets == null) {
            val msg = "Context lost, cannot serve assets."
            Logger.serverLog(msg)
            return getResponse(msg, status = Response.Status.INTERNAL_ERROR)
        }

        val path = when {
            !uri.contains(".") -> "$baseFolder/$uri/index.html"
            else -> "$baseFolder/$uri"
        }

        val mime = getMimeType(path.substringAfterLast(".", ""))

        try {
            val inputStream = assets.open(path)
            Logger.serverLog("Success")
            return newChunkedResponse(Response.Status.OK, mime, inputStream).allowCors()
        } catch (e: Exception) {
            Logger.serverError("Primary asset not found: $path", e)

            return try {
                val fallbackStream = assets.open("$baseFolder/index.html")
                Logger.serverLog("Fallback to index.html")
                newChunkedResponse(Response.Status.OK, "text/html", fallbackStream)
            } catch (fallbackException: Exception) {
                val message = "Fallback also failed, check logcat for more info"
                Logger.serverError(message, fallbackException)
                getResponse(message, status = Response.Status.INTERNAL_ERROR)
            }
        }
    }

    fun startServer() {
        try {
            start()
            Logger.serverLog("Server started on port $listeningPort")
        } catch (e: IOException) {
            Logger.serverError("Failed to start server", e)
        }
    }

    fun stopServer() {
        stop()
        Logger.serverLog("Server stopped")
    }
}