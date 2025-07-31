package com.bylazar.panels.reflection

import com.bylazar.panels.Logger
import com.bylazar.panels.reflection.ClassFinder
import com.bylazar.panels.reflection.ClassFinder.DEFAULT_IGNORED_PACKAGES
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.zip.ZipFile

object ClassFinder {
    val DEFAULT_IGNORED_PACKAGES: Set<String> = HashSet(
        mutableListOf(
            "com.google",
            "com.sun",
            "sun",
            "com.qualcomm",
            "org.opencv",
            "java",
            "javax",
            "android",
            "com.android",
            "kotlin",
            "dalvik",
            "org.firstinspires.ftc.robotcore",
            "org.intellij",
            "org.firstinspires.inspection",
            "org.firstinspires.ftc.robotserver",
            "org.firstinspires.ftc.ftccommon",
            "org.firstinspires.ftc.robotcontroller",
            "org.firstinspires.ftc.onbotjava",
            "org.firstinspires.ftc.vision",
            "org.firstinspires.ftc.apriltag",
            "org.slf4j",
            "org.threeten",
            "org.w3c",
            "org.xmlpull",
            "org.java_websocket",
            "fi.iki",
            "okhttp3",
            "com.journeyapps",
            "dk.sgjesse",
            "org.openjsse",
            "com.jakewharton",
            "org.openftc",
            "jdk.Exported",
            "org.json",
            "org.xml",
            "nl.minvws",
            "okio",
            "org.bouncycastle",
            "org.conscrypt",
            "org.jetbrains",
            "com.fasterxml",
            "okhttp3.internal",
            "kotlinx.coroutines",
        )
    )

    var apkPath: String = ""
    var allClasses: List<ClassEntry> = listOf()

    fun init(apk: String) {
        apkPath = apk
        val classSet = LinkedHashSet<ClassEntry>()
        try {
            ZipFile(apkPath).use { zipFile ->
                val entries = zipFile.entries()
                while (entries.hasMoreElements()) {
                    val entry = entries.nextElement()
                    if (entry.name.startsWith("classes") && entry.name.endsWith(".dex")) {
                        Logger.reflectionLog("Checking ${entry.name}")
                        zipFile.getInputStream(entry).use { inputStream ->
                            val dexBytes = inputStream.readBytes()
                            val dexBuffer =
                                ByteBuffer.wrap(dexBytes).order(ByteOrder.LITTLE_ENDIAN)
                            val classNames = dexBuffer.extractClassNamesFromDex()
                            val filteredClassNames = classNames.filter { className ->
                                val isNotIgnored =
                                    DEFAULT_IGNORED_PACKAGES.none { prefix ->
                                        className.startsWith(
                                            prefix
                                        )
                                    }
                                return@filter isNotIgnored
                            }
                            Logger.reflectionLog("Found ${filteredClassNames.size} classes in ${entry.name}")
                            val processedClassNames = filteredClassNames.map {
                                ClassEntry(
                                    className = it,
                                )
                            }
                            Logger.reflectionLog("Found ${processedClassNames.size} processed classes in ${entry.name}")
                            classSet.addAll(processedClassNames)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            Logger.reflectionError("IOException occurred: ${e.message}")
        } catch (e: IllegalArgumentException) {
            Logger.reflectionError("IllegalArgumentException occurred: ${e.message}")
        } catch (t: Throwable) {
            Logger.reflectionError("Throwable caught: ${t::class.simpleName} - ${t.message}")
        }
        allClasses = classSet.toList()
    }

    fun findClasses(
        shouldKeepFilter: (clazz: Class<*>) -> Boolean = { true },
    ): List<ClassEntry> {
        val processedClassNames = allClasses.mapNotNull {
            try {
                val clazz = Class.forName(it.className)
                val shouldKeep = shouldKeepFilter(clazz)
                if (!shouldKeep) {
                    return@mapNotNull null
                }
                ClassEntry(
                    className = it.className,
                )
            } catch (e: ClassNotFoundException) {
                Logger.reflectionError("Class not found: $it")
                null
            } catch (e: Exception) {
                Logger.reflectionError("Error loading class $it: ${e.message}")
                null
            } catch (t: Throwable) {
                Logger.reflectionError("Throwable caught: ${t::class.simpleName} - ${t.message}")
                null
            }
        }
        return processedClassNames
    }

    data class ClassEntry(
        val className: String,
    )
}