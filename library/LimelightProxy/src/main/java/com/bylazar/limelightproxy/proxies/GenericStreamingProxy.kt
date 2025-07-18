package com.bylazar.limelightproxy.proxies

import fi.iki.elonen.NanoHTTPD
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class GenericStreamingProxy(
    val outsidePort: Int,
    val innerPort: Int,
    val innerIP: String
) :
    NanoHTTPD(outsidePort) {
    private val client = OkHttpClient.Builder().build()

    override fun serve(session: IHTTPSession): Response {
        return try {
            handleReverseProxy(session)
        } catch (e: Exception) {
            println("PANELS: PLUGINS/COM.BYLAZAR.LIMELIGHTPROXY: Proxy error: ${e.message}")
            e.printStackTrace()
            newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                "text/plain",
                "Internal Server Error"
            )
        }
    }

    private fun handleReverseProxy(session: IHTTPSession): Response {
        println("PANELS: PLUGINS/COM.BYLAZAR.LIMELIGHTPROXY: Proxying request to ${session.uri}")

        val request = Request.Builder()
            .url("http://$innerIP:$innerPort${session.uri}")
            .method(session.method.name, getRequestBody(session))
            .apply {
                session.headers
                    .filterKeys { it.lowercase() != "host" }
                    .forEach { (key, value) -> addHeader(key, value) }
            }
            .build()

        val response = client.newCall(request).execute()

        val mimeType = response.header("Content-Type") ?: "application/octet-stream"
        val encoding = response.header("Content-Encoding")

        // Don't buffer the response body; stream it
        val responseBodyStream = response.body?.byteStream()
            ?: return newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                "text/plain",
                "Failed to get stream from response"
            )

        val chunkedResponse = newChunkedResponse(
            Response.Status.lookup(response.code) ?: Response.Status.INTERNAL_ERROR,
            mimeType,
            responseBodyStream
        )

        encoding?.let {
            chunkedResponse.addHeader("Content-Encoding", it)
        }

        listOf("Cache-Control", "Content-Language", "ETag", "Connection", "Transfer-Encoding")
            .forEach { header ->
                response.header(header)?.let { chunkedResponse.addHeader(header, it) }
            }

        return chunkedResponse
    }

    private fun getRequestBody(session: IHTTPSession): RequestBody? {
        if (session.method in listOf(Method.POST, Method.PUT, Method.PATCH)) {
            val buffer = session.inputStream.readBytes()
            val mediaType = session.headers["content-type"]?.toMediaTypeOrNull()
            return buffer.toRequestBody(mediaType)
        }
        return null
    }

    fun startServer() {
        if(isAlive) return
        start()
        println("PANELS: PLUGINS/COM.BYLAZAR.LIMELIGHTPROXY: Server started on port $outsidePort")
    }

    fun stopServer() {
        if(!isAlive) return
        stop()
        println("PANELS: PLUGINS/COM.BYLAZAR.LIMELIGHTPROXY: Server stopped")
    }
}