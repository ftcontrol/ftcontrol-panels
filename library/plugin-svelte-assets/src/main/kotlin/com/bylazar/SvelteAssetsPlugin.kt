package com.bylazar

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Exec
import java.io.File

open class SvelteAssetsPluginExtension {
    var webAppPath: String = "web"
    var buildDirPath: String = "build"
    var assetsPath: String = "web"
    var useNpm: Boolean = false
}

class SvelteAssetsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension =
            project.extensions.create("svelteAssets", SvelteAssetsPluginExtension::class.java)

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
        project.afterEvaluate {
            configure(project, extension)
        }
    }

    private fun configure(project: Project, extension: SvelteAssetsPluginExtension) {
        val isWindows = System.getProperty("os.name").lowercase().contains("windows")
        val webDir = File(project.projectDir, extension.webAppPath)
        val outputDir = File(project.projectDir, "src/main/assets/${extension.assetsPath}")

        val projectSuffix = project.name.replace(Regex("\\W+"), "")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        println("🔧 Configuring SvelteAssetsPlugin for ${project.path} with:")
        println("   webAppPath: ${extension.webAppPath}")
        println("   buildDirPath: ${extension.buildDirPath}")
        println("   assetsPath: ${extension.assetsPath}")
        println("   useNpm: ${extension.useNpm}")
        println("   webDir: ${webDir.absolutePath}")
        println("   outputDir: ${outputDir.absolutePath}")

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

        val clearAssets = project.tasks.register("clearSvelteAssets$projectSuffix", Delete::class.java) {
            group = "svelte"
            description = "Clears web assets for ${project.path}"
            val pluginsDir1 = File(project.projectDir, "src/main/assets/plugins")
            if (pluginsDir1.exists()) {
                delete(pluginsDir1.listFiles()?.toList() ?: emptyList<File>())
            }
            val pluginsDir2 = File(project.projectDir, "src/main/assets/${extension.assetsPath}")
            if (pluginsDir2.exists()) {
                delete(pluginsDir2.listFiles()?.toList() ?: emptyList<File>())
            }
        }

        val installSvelteApp = project.tasks.register("installSvelteApp$projectSuffix", Exec::class.java) {
            group = "svelte"
            description = "Installs web dependencies for ${project.path} (${if (extension.useNpm) "npm" else "bun"})"
            dependsOn(clearAssets)
            workingDir = webDir
            commandLine = installCmd
            isIgnoreExitValue = false
        }

        val buildSvelteApp = project.tasks.register("buildSvelteApp$projectSuffix", Exec::class.java) {
            group = "svelte"
            description = "Builds the Svelte app for ${project.path}"
            dependsOn(installSvelteApp)
            workingDir = webDir
            commandLine = buildCmd
            isIgnoreExitValue = false
        }

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
        }

        configurePublishingDependencies(project, buildSvelteAggregate)

        val root = project.rootProject
        val rootTask = root.tasks.findByName("buildAllSvelte")
            ?: root.tasks.create("buildAllSvelte") {
                group = "svelte"
                description = "Builds Svelte assets for all subprojects"
            }
        rootTask.dependsOn(buildSvelteAggregate)

        println("✅ SvelteAssetsPlugin tasks created for ${project.path}")
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
