import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create

class PublishingConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply("maven-publish")

        project.afterEvaluate {
            if (project.plugins.hasPlugin("java") || project.plugins.hasPlugin("kotlin")) {
                project.extensions.configure<org.gradle.api.publish.PublishingExtension>("publishing") {
                    repositories {
                        mavenLocal()
                    }
                    publications {
                        create<MavenPublication>("mavenJava") {
                            from(project.components.findByName("java"))
                            artifactId = project.name
                        }
                    }
                }
            }
        }
    }
}