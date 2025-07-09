package com.bylazar.panels.reflection

import com.bylazar.panels.Logger
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.zip.ZipFile

class ClassFinder(
    val shouldKeepFilter: (clazz: Class<*>) -> Boolean = { true },
    val ignoredPackages: Set<String> = DEFAULT_IGNORED_PACKAGES,
) {
    companion object {
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
                "com.fasterxml"
            )
        )
    }

    data class ClassEntry(
        val className: String,
    )

    var classes: List<ClassEntry> = listOf()

    fun findClasses(
        apkPath: String,
        shouldKeepFilter: (clazz: Class<*>) -> Boolean = { true },
        ignoredPackages: Set<String> = DEFAULT_IGNORED_PACKAGES,
    ): List<ClassEntry> {
        val instance = ClassFinder(shouldKeepFilter, ignoredPackages)
        instance.updateClasses(apkPath)
        return instance.classes
    }

    fun updateClasses(apkPath: String) {
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
                                    ignoredPackages.none { prefix -> className.startsWith(prefix) }
                                return@filter isNotIgnored
                            }
                            Logger.reflectionLog("Found ${filteredClassNames.size} classes in ${entry.name}")
                            val processedClassNames = filteredClassNames.mapNotNull {
                                try {
                                    val clazz = Class.forName(it)
                                    val shouldKeep = shouldKeepFilter(clazz)
                                    if (!shouldKeep) {
                                        return@mapNotNull null
                                    }
                                    ClassEntry(
                                        className = it,
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
        classes = classSet.toList()
    }
}