package com.bylazar.panels.server

import android.content.Context
import android.content.res.AssetManager
import android.provider.Browser.sendString
import com.bylazar.panels.Logger
import com.bylazar.panels.json.PluginData
import com.bylazar.panels.json.createSocketMessage
import com.bylazar.panels.plugins.PluginsManager
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import java.lang.ref.WeakReference

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


    override fun serve(session: IHTTPSession): Response {
        if (session.method == Method.OPTIONS) {
            return getResponse("")
        }

        val uri = session.uri.removePrefix("/").removeSuffix("/").removePrefix("index.html")
            .ifEmpty { "index.html" }

        if (uri == "plugins") {
            if (!PluginsManager.isRegistered) {
                return getResponse("null", contentType = "application/json").allowCors()
            }

            val jsonString = createSocketMessage(
                "core",
                "pluginsDetails",
                PluginData(
                    PluginsManager.plugins.values.map { it.toInfo() }
                )
            ).toJson()

            return getResponse(jsonString, contentType = "application/json").allowCors()
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
        else -> "application/octet-stream"
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
            Logger.serverLog("Primary asset not found: $path — ${e.message}")

            return try {
                val fallbackStream = assets.open("$baseFolder/index.html")
                Logger.serverLog("Fallback to index.html")
                newChunkedResponse(Response.Status.OK, "text/html", fallbackStream)
            } catch (fallbackException: Exception) {
                val message = "Fallback also failed: ${fallbackException.message}"
                Logger.serverLog(message)
                getResponse(message, status = Response.Status.INTERNAL_ERROR)
            }
        }
    }

    fun startServer() {
        try {
            start()
            Logger.serverLog("Server started on port $listeningPort")
        } catch (e: IOException) {
            Logger.serverLog("Failed to start server: ${e.message}")
        }
    }

    fun stopServer() {
        stop()
        Logger.serverLog("Server stopped")
    }
}