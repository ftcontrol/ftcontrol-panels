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
}

class SvelteAssetsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("svelteAssets", SvelteAssetsPluginExtension::class.java)

        project.plugins.withId("com.android.library") {
            configure(project)
        }
        project.plugins.withId("com.android.application") {
            configure(project)
        }
    }

    private fun configure(project: Project) {
        val extension = project.extensions.getByType(SvelteAssetsPluginExtension::class.java)
        val isWindows = System.getProperty("os.name").lowercase().contains("windows")
        val webDir = File(project.projectDir, extension.webAppPath)
        val outputDir = File(project.projectDir, "src/main/assets/${extension.assetsPath}")

        val clearAssets = project.tasks.register("clearAssets", Delete::class.java) {
            delete(outputDir.listFiles())
        }

        val installSvelteApp = project.tasks.register("installSvelteApp", Exec::class.java) {
            dependsOn(clearAssets)
            workingDir = webDir
            commandLine = if (isWindows) {
                listOf("cmd", "/c", "bun", "install")
            } else {
                listOf("sh", "-c", "bun install")
            }
        }

        val buildSvelteApp = project.tasks.register("buildSvelteApp", Exec::class.java) {
            dependsOn(installSvelteApp)
            workingDir = webDir
            commandLine = if (isWindows) {
                listOf("cmd", "/c", "bun", "run", "build")
            } else {
                listOf("sh", "-c", "bun run build")
            }
        }

        val copySvelteToAssets = project.tasks.register("copySvelteToAssets", Copy::class.java) {
            dependsOn(buildSvelteApp)
            from(File(webDir, extension.buildDirPath))
            into(outputDir)
        }

        project.tasks.matching { it.name == "preBuild" }.configureEach {
            dependsOn(copySvelteToAssets)
        }

        println("✅ SvelteAssetsPlugin applied to ${project.name}")
    }
}
