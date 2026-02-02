package com.bylazar.tasks

import org.gradle.api.tasks.TaskAction

abstract class InstallBunLocally : BunTask() {
    init {
        group = "frontend"
        onlyIf { !bunInstalled }
    }

    @TaskAction
    fun installBun() {
        project.exec {
            commandLine = if (isWindows) listOf("powershell", "-c", "irm bun.sh/install.ps1|iex")
            else listOf("sh", "-c", "curl -fsSL https://bun.com/install | bash\n")
            environment["BUN_INSTALL"] = BUN_INSTALL.absolutePath
            standardOutput = System.out
            errorOutput = System.err
        }
    }
}