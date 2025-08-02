package com.bylazar.panels

import org.junit.Assert
import org.junit.Test
import org.tukaani.xz.LZMA2Options
import org.tukaani.xz.LZMAInputStream
import org.tukaani.xz.LZMAOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.zip.Deflater
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.Inflater


class TestCompression {

    fun loadResourceAsBytes(resourceName: String): ByteArray {
        return this::class.java.classLoader?.getResource(resourceName)
            ?.openStream()
            ?.readBytes()
            ?: throw FileNotFoundException("Resource not found: $resourceName")
    }

    private val testData: ByteArray = loadResourceAsBytes("sample.json")

    @Test
    fun test() {
        testAlgorithm("Gzip", ::compressGzip, ::decompressGzip)
        testAlgorithm("LZMA", ::compressLzma) { input, _ -> decompressLzma(input) }
        testAlgorithm("Deflate", ::compressDeflate, ::decompressDeflate)
    }

    private fun testAlgorithm(
        name: String,
        compress: (ByteArray) -> ByteArray,
        decompress: (ByteArray, Int) -> ByteArray
    ) {
        println("=== $name ===")
        val startCompress = System.nanoTime()
        val compressed = compress(testData)
        val endCompress = System.nanoTime()

        val startDecompress = System.nanoTime()
        val decompressed = decompress(compressed, testData.size)
        val endDecompress = System.nanoTime()

        Assert.assertArrayEquals("$name decompressed data mismatch!", testData, decompressed)

        println("Original: ${testData.size / 1024 / 1024} MB")
        println("Compressed: ${compressed.size / 1024 / 1024} MB")
        println("Ratio: %.2fx".format(testData.size.toDouble() / compressed.size))
        println("Compress time: %.2f ms".format((endCompress - startCompress) / 1e6))
        println("Decompress time: %.2f ms".format((endDecompress - startDecompress) / 1e6))
        println()
    }

    private fun compressGzip(input: ByteArray): ByteArray {
        val output = ByteArrayOutputStream()
        GZIPOutputStream(output).use { it.write(input) }
        return output.toByteArray()
    }

    private fun decompressGzip(input: ByteArray, originalSize: Int): ByteArray {
        val inputStream = ByteArrayInputStream(input)
        return GZIPInputStream(inputStream).readAllBytes()
    }

    fun compressLzma(input: ByteArray): ByteArray {
        val baos = ByteArrayOutputStream()
        val options = LZMA2Options().apply { setPreset(3) }
        LZMAOutputStream(baos, options, -1).use { it.write(input) }
        return baos.toByteArray()
    }

    fun decompressLzma(input: ByteArray): ByteArray {
        val bais = ByteArrayInputStream(input)
        return LZMAInputStream(bais).readAllBytes()
    }

    fun compressDeflate(input: ByteArray): ByteArray {
        val deflater = Deflater(Deflater.BEST_COMPRESSION)
        deflater.setInput(input)
        deflater.finish()
        val output = ByteArray(input.size)
        val compressedSize = deflater.deflate(output)
        deflater.end()
        return output.copyOf(compressedSize)
    }

    fun decompressDeflate(input: ByteArray, originalSize: Int): ByteArray {
        val inflater = Inflater()
        inflater.setInput(input)
        val output = ByteArray(originalSize)
        inflater.inflate(output)
        inflater.end()
        return output
    }
}
