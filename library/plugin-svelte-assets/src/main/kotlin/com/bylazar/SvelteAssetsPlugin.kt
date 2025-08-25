package com.bylazar

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.GradleException
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class SvelteAssetsPluginExtension {
    var webAppPath: String = "web"
    var buildDirPath: String = "build"
    var assetsPath: String = "web"
    var useNpm: Boolean = false
    var maxParallelSvelteBuilds: Int = 4
}

abstract class SvelteSingleSlotService :
    BuildService<BuildServiceParameters.None>, AutoCloseable {
    override fun close() {}
}

class TeeOutputStream(
    private val a: OutputStream,
    private val b: OutputStream
) : OutputStream() {
    override fun write(bte: Int) {
        a.write(bte); b.write(bte)
    }
    override fun write(btes: ByteArray) {
        a.write(btes); b.write(btes)
    }
    override fun write(btes: ByteArray, off: Int, len: Int) {
        a.write(btes, off, len); b.write(btes, off, len)
    }
    override fun flush() { a.flush(); b.flush() }
    override fun close() { a.close(); b.close() }
}

private object StdOutStream : OutputStream() {
    override fun write(b: Int) = System.out.write(b)
    override fun write(b: ByteArray, off: Int, len: Int) = System.out.write(b, off, len)
}

private object StdErrStream : OutputStream() {
    override fun write(b: Int) = System.err.write(b)
    override fun write(b: ByteArray, off: Int, len: Int) = System.err.write(b, off, len)
}

class SvelteAssetsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("svelteAssets", SvelteAssetsPluginExtension::class.java)

        project.plugins.withId("com.android.library") {
            configureAfterEvaluate(project, extension)
        }
        project.plugins.withId("com.android.application") {
            configureAfterEvaluate(project, extension)
        }

        project.afterEvaluate {
            if (!project.plugins.hasPlugin("com.android.library")
                && !project.plugins.hasPlugin("com.android.application")
            ) {
                configure(project, extension)
            }
        }
    }

    private fun configureAfterEvaluate(project: Project, extension: SvelteAssetsPluginExtension) {
        project.afterEvaluate { configure(project, extension) }
    }

    private fun configure(project: Project, extension: SvelteAssetsPluginExtension) {
        val isWindows = System.getProperty("os.name").lowercase().contains("windows")
        val webDir = File(project.projectDir, extension.webAppPath)
        val outputDir = File(project.projectDir, "src/main/assets/${extension.assetsPath}")

        val singleSlot = project.gradle.sharedServices
            .registerIfAbsent("svelteSingleSlot", SvelteSingleSlotService::class.java) {
                maxParallelUsages.set(extension.maxParallelSvelteBuilds.coerceAtLeast(1))
            }

        val projectSuffix = project.name.replace(Regex("\\W+"), "")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        val installCmd = when {
            extension.useNpm && isWindows -> listOf("cmd", "/c", "npm", "i")
            extension.useNpm -> listOf("sh", "-c", "npm i")
            !extension.useNpm && isWindows -> listOf("cmd", "/c", "bun", "i")
            else -> listOf("sh", "-c", "bun i")
        }
        val buildCmd = when {
            extension.useNpm && isWindows -> listOf("cmd", "/c", "npm", "run", "build")
            extension.useNpm -> listOf("sh", "-c", "npm run build")
            !extension.useNpm && isWindows -> listOf("cmd", "/c", "bun", "run", "build")
            else -> listOf("sh", "-c", "bun run build")
        }

        val clearSvelte = project.tasks.register("clearSvelteAssets$projectSuffix", Delete::class.java) {
            group = "svelte"
            description = "Clears web assets for ${project.path}"
            val pluginsDir1 = File(project.projectDir, "src/main/assets/plugins")
            if (pluginsDir1.exists()) delete(pluginsDir1.listFiles()?.toList() ?: emptyList<File>())
            val pluginsDir2 = File(project.projectDir, "src/main/assets/${extension.assetsPath}")
            if (pluginsDir2.exists()) delete(pluginsDir2.listFiles()?.toList() ?: emptyList<File>())
        }

        val installSvelteApp = project.tasks.register("installSvelteApp$projectSuffix", Exec::class.java) {
            group = "svelte"
            description = "Installs web dependencies for ${project.path} (${if (extension.useNpm) "npm" else "bun"})"
            dependsOn(clearSvelte)
            workingDir = webDir
            commandLine = installCmd
            isIgnoreExitValue = true
            usesService(singleSlot)
        }
        attachExecLogging(project, installSvelteApp, "install")

        val buildSvelteApp = project.tasks.register("buildSvelteApp$projectSuffix", Exec::class.java) {
            group = "svelte"
            description = "Builds the Svelte app for ${project.path}"
            dependsOn(installSvelteApp)
            workingDir = webDir
            commandLine = buildCmd
            isIgnoreExitValue = true
            usesService(singleSlot)
        }
        attachExecLogging(project, buildSvelteApp, "build")

        val copySvelteToAssets = project.tasks.register("copySvelteToAssets$projectSuffix", Copy::class.java) {
            group = "svelte"
            description = "Copies the built Svelte assets into ${outputDir.relativeTo(project.projectDir)} for ${project.path}"
            dependsOn(buildSvelteApp)
            from(File(webDir, extension.buildDirPath))
            into(outputDir)
        }

        val buildSvelteAggregate = project.tasks.register("buildSvelte$projectSuffix") {
            group = "svelte"
            description = "Installs, builds, and copies Svelte assets for ${project.path}"
            dependsOn(copySvelteToAssets)
            usesService(singleSlot)
        }

        configurePublishingDependencies(project, buildSvelteAggregate)

        val root = project.rootProject
        val rootTask = root.tasks.findByName("buildAllSvelte")
            ?: root.tasks.create("buildAllSvelte") {
                group = "svelte"
                description = "Builds Svelte assets for all subprojects"
            }
        rootTask.dependsOn(buildSvelteAggregate)

        val extra = root.extensions.extraProperties
        val keyAggregates = "svelteAggregates"
        val keyWired = "svelteAggregatesWired"

        @Suppress("UNCHECKED_CAST")
        val aggregates: MutableList<org.gradle.api.tasks.TaskProvider<Task>> =
            if (extra.has(keyAggregates)) {
                extra.get(keyAggregates) as MutableList<org.gradle.api.tasks.TaskProvider<Task>>
            } else {
                mutableListOf<org.gradle.api.tasks.TaskProvider<Task>>().also {
                    extra.set(keyAggregates, it)
                }
            }
        aggregates.add(buildSvelteAggregate)

        if (!extra.has(keyWired)) {
            extra.set(keyWired, true)
            root.gradle.projectsEvaluated {
                @Suppress("UNCHECKED_CAST")
                val allProviders = (extra.get(keyAggregates)
                        as MutableList<org.gradle.api.tasks.TaskProvider<Task>>).toList()
                if (allProviders.isNotEmpty()) {
                    val allTasks = allProviders.map { it.get() }.sortedWith(
                        compareBy<Task> { it.project.path }.thenBy { it.name }
                    )
                    for (i in 1 until allTasks.size) {
                        allTasks[i].dependsOn(allTasks[i - 1])
                    }
                    rootTask.setDependsOn(listOf(allTasks.last()))
                }
                root.tasks.matching { it.name == "publish" }.configureEach {
                    dependsOn(rootTask)
                }
            }
        }
    }

    private fun attachExecLogging(
        project: Project,
        taskProvider: org.gradle.api.tasks.TaskProvider<Exec>,
        label: String
    ) {
        taskProvider.configure {
            val relProjectPath = project.path.removePrefix(":").ifEmpty { "root" }
            val safePath = relProjectPath.replace(':', '_')
            val logsDir = File(project.buildDir, "svelte-logs/$safePath").also { it.mkdirs() }

            val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
            val latestLog = File(logsDir, "$label.log")
            val tsLog = File(logsDir, "$label-$timestamp.log")

            doFirst {
                logsDir.mkdirs()
                project.logger.log(
                    LogLevel.LIFECYCLE,
                    "[svelte] ${project.path} ${name}: logging to ${latestLog.relativeTo(project.projectDir)} (and ${tsLog.name})"
                )
                val fout = FileOutputStream(latestLog, false)
                val fts = FileOutputStream(tsLog, false)
                val fileTee = TeeOutputStream(fout, fts)
                val stdoutTee = TeeOutputStream(StdOutStream, fileTee)
                val stderrTee = TeeOutputStream(StdErrStream, fileTee)
                standardOutput = stdoutTee
                errorOutput = stderrTee
                val header = "===== ${project.path} :: $name ($label) :: $timestamp =====\n"
                stdoutTee.write(header.toByteArray())
                stdoutTee.flush()
            }

            doLast {
                val result = executionResult.get()
                val exit = result.exitValue
                if (exit != 0) {
                    val tailLines = 200
                    val lines = if (latestLog.exists()) latestLog.readLines() else emptyList()
                    val tail = lines.takeLast(tailLines)
                    project.logger.error(
                        "\n\u001B[31m[svelte] ${project.path} ${name} failed (exit $exit). " +
                                "Showing last $tailLines log lines from ${latestLog.relativeTo(project.projectDir)}:\u001B[0m"
                    )
                    tail.forEach { project.logger.error(it) }
                    throw GradleException(
                        "[svelte] ${project.path} ${name} failed. Full log: ${latestLog.absolutePath}"
                    )
                }
            }
        }
    }

    private fun configurePublishingDependencies(
        project: Project,
        buildSvelteAggregate: org.gradle.api.tasks.TaskProvider<org.gradle.api.Task>
    ) {
        project.plugins.withId("maven-publish") {
            project.tasks.configureEach {
                val n = this.javaClass.name
                if (n == "org.gradle.api.publish.tasks.PublishToMavenRepository" ||
                    n == "org.gradle.api.publish.tasks.PublishToMavenLocal"
                ) {
                    dependsOn(buildSvelteAggregate)
                }
            }
            project.tasks.matching { it.name == "publish" }.configureEach {
                dependsOn(buildSvelteAggregate)
            }
        }
        project.tasks.matching { it.name.equals("uploadArchives", ignoreCase = true) }.configureEach {
            dependsOn(buildSvelteAggregate)
        }
    }
}
