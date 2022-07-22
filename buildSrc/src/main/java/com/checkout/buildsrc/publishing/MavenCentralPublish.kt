package com.checkout.buildsrc.publishing

import com.checkout.buildsrc.utils.getProperty
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.tasks.GenerateModuleMetadata
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType

private const val SONATYPE_SNAPSHOTS_REPO_URL =
    "https://oss.sonatype.org/content/repositories/snapshots/"
private const val SONATYPE_STAGING_REPO_URL =
    "https://oss.sonatype.org/service/local/staging/deploy/maven2/"

fun Project.configureMavenPublication(
    name: String,
    configProvider: () -> PublishComponentInformation
) {
    apply(plugin = "maven-publish")
    apply(plugin = "org.gradle.signing")

    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }

    afterEvaluate {
        val publishComponent = configProvider()

        publishing.apply {
            repositories {
                maven {
                    this.name = "Sonatype"
                    this.url = if (publishComponent.isSnapshotRelease) {
                        uri(SONATYPE_SNAPSHOTS_REPO_URL)
                    } else {
                        uri(SONATYPE_STAGING_REPO_URL)
                    }
                    credentials {
                        username = getProperty("ossrhUsername")
                        password = getProperty("ossrhPassword")
                    }
                }
            }

            publications {

                register(name, MavenPublication::class.java) {
                    from(components.getByName("release"))
                    groupId = publishComponent.groupId
                    artifactId = publishComponent.artifactId
                    version = publishComponent.version

                    pom {
                        MavenPublishingHelper.updatePom(this, publishComponent)
                    }
                }
            }
        }

        signing.apply {
            if (project.hasProperty("signing.keyId")
                && project.hasProperty("signing.password")
                && project.hasProperty("signing.secretKeyRingFile")
            ) {
                sign(publishing.publications)
            }
        }
    }
}

private val Project.publishing: PublishingExtension
    get() =
        (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("publishing") as PublishingExtension

val Project.signing: org.gradle.plugins.signing.SigningExtension
    get() =
        (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("signing") as org.gradle.plugins.signing.SigningExtension
