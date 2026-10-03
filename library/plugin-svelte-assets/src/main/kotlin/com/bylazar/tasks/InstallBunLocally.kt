package com.bylazar.tasks

import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class InstallBunLocally @Inject constructor( execOperations: ExecOperations ) : BunTask(execOperations) {
    init {
        group = "frontend"
        onlyIf { !bunInstalled }
    }

    @TaskAction
    fun installBun() {
        execOperations.exec {
            commandLine = if (isWindows) listOf("powershell", "-c", "irm bun.sh/install.ps1|iex")
            else listOf("sh", "-c", "curl -fsSL https://bun.com/install | bash\n")
            environment["BUN_INSTALL"] = BUN_INSTALL.absolutePath
            standardOutput = System.out
            errorOutput = System.err
        }
    }
}