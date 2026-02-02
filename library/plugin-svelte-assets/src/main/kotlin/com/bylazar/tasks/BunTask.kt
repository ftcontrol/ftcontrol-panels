package com.bylazar.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Internal
import org.gradle.process.ExecSpec
import java.io.File

@CacheableTask
abstract class BunTask : DefaultTask() {
    companion object {
        val isWindows = System.getProperty("os.name").lowercase().contains("windows")
    }

    @Suppress("PropertyName")
    @get:Internal
    val BUN_INSTALL = project.rootProject.file(".bun")

    @get:Internal
    val bunInstalledLocally
        get() = (isWindows && File(BUN_INSTALL, "bin\\bun.exe").exists())//
                || (!isWindows && File(BUN_INSTALL, "bin/bun").exists())

    @get:Internal
    val bunInstalled
        get() = System.getenv("BUN_INSTALL") != null || bunInstalledLocally

    /**
     * executes [cmd] under powershell or sh,
     * with the appropriate env vars set to use the locally installed bun
     * if necessary
     */
    fun bunExec(cmd: String, action: ExecSpec.() -> Unit = {}) {
        project.exec {
            commandLine = if (isWindows) listOf("powershell", "-c", cmd)
            else listOf("sh", "-c", cmd)
            if (bunInstalledLocally) {
                environment["BUN_INSTALL"] = BUN_INSTALL.absolutePath
                environment["PATH"] =
                    if (isWindows) "${BUN_INSTALL.absolutePath}\\bin;${System.getenv("PATH")}"
                    else "${BUN_INSTALL.absolutePath}/bin:${System.getenv("PATH")}"
            }
            action()
        }
    }
}