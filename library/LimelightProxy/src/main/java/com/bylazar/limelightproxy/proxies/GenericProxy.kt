package com.bylazar.limelightproxy.proxies

import fi.iki.elonen.NanoHTTPD
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayInputStream

class GenericProxy(
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
                session.headers.forEach { (key, value) ->
                    if (!key.equals("host", ignoreCase = true)) {
                        addHeader(key, value)
                    }
                }
            }
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.bytes() ?: ByteArray(0)

        val mimeType = response.header("Content-Type") ?: "application/json"
        val encoding = response.header("Content-Encoding")

        val resp = newFixedLengthResponse(
            Response.Status.lookup(response.code) ?: Response.Status.INTERNAL_ERROR,
            mimeType,
            ByteArrayInputStream(responseBody),
            responseBody.size.toLong()
        )

        encoding?.let {
            resp.addHeader("Content-Encoding", it)
        }

        listOf(
            "Cache-Control",
            "Content-Language",
            "ETag",
            "Content-Type",
            "Content-Encoding",
            "Content-Length"
        ).forEach { header ->
            response.header(header)?.let { resp.addHeader(header, it) }
        }

        resp.addHeader("Access-Control-Allow-Origin", "*")
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        resp.addHeader("Access-Control-Allow-Headers", "*")

        if (session.method == Method.OPTIONS) {
            val optionsResp = newFixedLengthResponse(Response.Status.OK, "text/plain", "")
            optionsResp.addHeader("Access-Control-Allow-Origin", "*")
            optionsResp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            optionsResp.addHeader("Access-Control-Allow-Headers", "*")
            return optionsResp
        }

        return resp
    }


    private fun getRequestBody(session: IHTTPSession): RequestBody? {
        return if (session.method in listOf(Method.POST, Method.PUT, Method.PATCH)) {
            val contentLength = session.headers["content-length"]?.toIntOrNull() ?: 0
            val buffer = ByteArray(contentLength)
            session.inputStream.read(buffer)
            val mediaType = session.headers["content-type"]?.toMediaTypeOrNull()
            buffer.toRequestBody(mediaType)
        } else null
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