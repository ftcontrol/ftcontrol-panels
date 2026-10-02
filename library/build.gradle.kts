fun makePublishAllTask(repository: String) = tasks.register("publishAllReleasePublicationsTo$repository") {
    group = "Publishing"
    description = "publish all release publications except ExamplePlugin to $repository"
    subprojects.forEach { project ->
        if (project.name == "ExamplePlugin") return@forEach
        if (project.name == "TeamCode") return@forEach
        if (project.name == "FtcRobotController") return@forEach
        if (project.name == "plugin-svelte-assets") return@forEach
        dependsOn(project.tasks.getByName("publishReleasePublicationTo$repository"))
    }
}

makePublishAllTask("MavenLocal")
makePublishAllTask("DairyRepository")
