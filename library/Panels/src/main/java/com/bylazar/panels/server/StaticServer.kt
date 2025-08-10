package com.bylazar.panels.server

import android.content.Context
import android.content.res.AssetManager
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.json.PluginData
import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.plugins.PluginsManager
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import java.lang.ref.WeakReference
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.LZMAOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.zip.Deflater

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

    fun compressDeflate(input: String): ByteArray {
        val inputBytes = input.toByteArray(Charsets.UTF_8)
        val deflater = Deflater(Deflater.BEST_COMPRESSION)
        deflater.setInput(inputBytes)
        deflater.finish()
        val output = ByteArray(inputBytes.size)
        val compressedSize = deflater.deflate(output)
        deflater.end()
        return output.copyOf(compressedSize)
    }

    fun ByteArray.sha256Hex(): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(this)
            .joinToString("") { "%02x".format(it) }
    }

    var response = compressDeflate("null")
    var jsonString = "null"
    var lastSha = "null"

    fun precompressData() {
        val t0 = System.currentTimeMillis()
        val pluginInfos = PluginsManager.plugins.values.map { it.toInfo() }.sortedBy { it.details.id }
        val t1 = System.currentTimeMillis()
        val skipped = PluginsManager.skippedPlugins.values.toList().sortedBy { it.id }
        val t2 = System.currentTimeMillis()

        jsonString = SocketMessage(
            "core",
            "pluginsDetails",
            PluginData(pluginInfos, skipped)
        ).toJson()
        val t3 = System.currentTimeMillis()

        val compressed = compressDeflate(jsonString)

        val t4 = System.currentTimeMillis()

        Logger.serverLog("toInfo() took ${t1 - t0}ms")
        Logger.serverLog("skippedPlugins took ${t2 - t1}ms")
        Logger.serverLog("toJson() took ${t3 - t2}ms")
        Logger.serverLog("Compression took ${t4 - t3}ms")
        Logger.serverLog("Total time: ${t4 - t0}ms")

        Logger.serverLog("Sending ${jsonString.length} characters (~${jsonString.toByteArray().size / 1024} KB)")
        Logger.serverLog("Sending compressed (~${compressed.size / 1024} KB)")
        response = compressed
        lastSha = compressed.sha256Hex()
    }

    override fun serve(session: IHTTPSession): Response {
        if (session.method == Method.OPTIONS) {
            return getResponse("")
        }

        val uri = session.uri.removePrefix("/").removeSuffix("/").removePrefix("index.html")
            .ifEmpty { "index.html" }

        if (uri == "api/plugins") {
            return newFixedLengthResponse(
                Response.Status.OK,
                "application/octet-stream",
                ByteArrayInputStream(response),
                response.size.toLong()
            ).allowCors()
        }
        if (uri == "api/plugins-json") {
            return getResponse(jsonString, "application/json").allowCors()
        }
        if (uri == "api/sha256") {
            return getResponse(lastSha).allowCors()
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