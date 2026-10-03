import org.gradle.api.publish.PublishingExtension

subprojects {
    plugins.withId("maven-publish") {
        group = "com.bylazar"
        extensions.configure<PublishingExtension> {
            repositories {
                maven {
                    name = "localDevRepo"
                    url = rootProject.file("../../ftcontrol-maven/releases").toURI()
                }
            }
        }
    }
}

fun makePublishAllTask(repository: String) = tasks.register("publishAllReleasePublicationsTo$repository") {
    group = "Publishing"
    description = "publish all release publications except ExamplePlugin to $repository"
    subprojects.forEach { project ->
        if (project.name == "ExamplePlugin") return@forEach
        if (project.name == "TeamCode") return@forEach
        if (project.name == "FtcRobotController") return@forEach
        if (project.name == "plugin-svelte-assets") return@forEach
        dependsOn("${project.path}:publishReleasePublicationTo$repository")
    }
}

makePublishAllTask("MavenLocal")
makePublishAllTask("LocalDevRepoRepository")
