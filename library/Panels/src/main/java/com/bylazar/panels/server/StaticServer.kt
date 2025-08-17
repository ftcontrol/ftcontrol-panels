package com.bylazar.panels.server

import android.content.Context
import android.content.res.AssetManager
import com.bylazar.panels.Logger
import com.bylazar.panels.Panels
import com.bylazar.panels.TaskTimer
import com.bylazar.panels.json.PluginData
import com.bylazar.panels.json.SocketMessage
import com.bylazar.panels.plugins.PluginsManager
import fi.iki.elonen.NanoHTTPD
import java.io.IOException
import java.lang.ref.WeakReference
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.zip.Deflater
import java.io.File

class StaticServer(
    context: Context,
    port: Int,
    private val baseFolder: String
) : NanoHTTPD(port) {
    private val contextRef = WeakReference(context)

    private val assetManager: AssetManager?
        get() = contextRef.get()?.assets

    private fun getCacheFolder(): File? {
        val ctx = contextRef.get() ?: return null
        val dir = File(ctx.cacheDir, "panels_static_cache")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    private fun cacheBinFile(): File? = getCacheFolder()?.let { File(it, "plugins_compressed.bin") }
    private fun cacheMetaFile(): File? =
        getCacheFolder()?.let { File(it, "plugins_compressed.meta") }

    private fun writeCache(compressed: ByteArray, compressedSha: String, jsonSha: String) {
        val bin = cacheBinFile()
        val meta = cacheMetaFile()
        if (bin == null || meta == null) return

        runCatching { bin.delete() }
        runCatching { meta.delete() }

        bin.outputStream().use { it.write(compressed) }
        val metaContent = buildString {
            appendLine("jsonSha=$jsonSha")
            appendLine("compressedSha=$compressedSha")
        }
        meta.writeText(metaContent, Charsets.UTF_8)
    }

    private fun readCacheIfValid(jsonSha: String): Pair<ByteArray, String>? {
        val bin = cacheBinFile() ?: return null
        val meta = cacheMetaFile() ?: return null
        if (!bin.exists() || !meta.exists()) return null

        val metaText = runCatching { meta.readText(Charsets.UTF_8) }.getOrNull() ?: return null
        val lines = metaText.lineSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() && '=' in it }
            .associate { it.substringBefore('=').trim() to it.substringAfter('=').trim() }

        val cachedJsonSha = lines["jsonSha"] ?: return null
        val cachedCompressedSha = lines["compressedSha"] ?: return null
        if (cachedJsonSha != jsonSha) return null

        val bytes = runCatching { bin.readBytes() }.getOrNull() ?: return null
        val bytesSha = bytes.sha256Hex()
        if (bytesSha != cachedCompressedSha) return null

        return bytes to cachedCompressedSha
    }


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
        val buffer = ByteArray(64 * 1024)
        val inputBytes = input.toByteArray(Charsets.UTF_8)
        val deflater = Deflater(Deflater.DEFAULT_COMPRESSION)
        deflater.reset()
        deflater.setInput(inputBytes)
        deflater.finish()
        val baos = ByteArrayOutputStream(inputBytes.size / 4 + 128)
        while (!deflater.finished()) {
            val n = deflater.deflate(buffer)
            baos.write(buffer, 0, n)
        }
        deflater.reset()
        return baos.toByteArray()
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

    var response = compressDeflate("null")
    var jsonString = "null"
    var lastSha = "null"

    fun precompressData() {
        TaskTimer.start("building plugin info")
        val t0 = System.currentTimeMillis()
        val pluginsString = PluginsManager.plugins.entries
            .sortedBy { it.key }
            .map { it.value.toInfo() }
            .toJsonArray { it }

        val t1 = System.currentTimeMillis()
        val skippedPluginsString = PluginsManager.skippedPluginsStrings.entries
            .sortedBy { it.key }
            .map { it.value }
            .toJsonArray { it }

        val t2 = System.currentTimeMillis()

        val sb = StringBuilder(256 + pluginsString.length + skippedPluginsString.length)
        sb.append("{\"messageID\":\"pluginsDetails\",\"pluginID\":\"core\",\"data\":{")
            .append("\"plugins\":").append(pluginsString).append(',')
            .append("\"skippedPlugins\":").append(skippedPluginsString)
            .append("}}")

        jsonString = sb.toString()

        val t3 = System.currentTimeMillis()

        val jsonSha = jsonString.sha256Hex()

        TaskTimer.end("building plugin info")
        TaskTimer.start("compressing data")
        val cacheHit = readCacheIfValid(jsonSha)
        if (cacheHit != null) {
            response = cacheHit.first
            lastSha = cacheHit.second
            val t4 = System.currentTimeMillis()

            Logger.serverLog("toInfo() took ${t1 - t0}ms")
            Logger.serverLog("skippedPlugins took ${t2 - t1}ms")
            Logger.serverLog("toJson() took ${t3 - t2}ms")
            Logger.serverLog("Cache HIT: reused compressed payload (no recompression).")
            Logger.serverLog("Total time: ${t4 - t0}ms")

            Logger.serverLog("Sending ${jsonString.length} characters (~${jsonString.toByteArray().size / 1024} KB)")
            Logger.serverLog("Sending compressed (~${response.size / 1024} KB)")
            TaskTimer.end("compressing data")
            return
        }

        val compressed = compressDeflate(jsonString)
        response = compressed
        lastSha = compressed.sha256Hex()

        writeCache(compressed, lastSha, jsonSha)

        val t4 = System.currentTimeMillis()
        Logger.serverLog("toInfo() took ${t1 - t0}ms")
        Logger.serverLog("skippedPlugins took ${t2 - t1}ms")
        Logger.serverLog("toJson() took ${t3 - t2}ms")
        Logger.serverLog("Compression took ${t4 - t3}ms")
        Logger.serverLog("Cache MISS: wrote new cache (single file policy).")
        Logger.serverLog("Total time: ${t4 - t0}ms")

        Logger.serverLog("Sending ${jsonString.length} characters (~${jsonString.toByteArray().size / 1024} KB)")
        Logger.serverLog("Sending compressed (~${compressed.size / 1024} KB)")
        TaskTimer.end("compressing data")
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

        for (id in PluginsManager.plugins.keys) {
            if (uri == "api/simple-configs/${id}") {
                val context = contextRef.get()
                if (context != null) {
                    val data = PluginsManager.loadPluginSimpleConfigString(context, id)
                    return getResponse(
                        data,
                        "application/json"
                    ).allowCors()
                }
            }
            if (uri == "api/configs/${id}") {
                val context = contextRef.get()
                if (context != null) {
                    val data = PluginsManager.loadPluginConfigString(context, id)
                    return getResponse(
                        data,
                        "application/json"
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