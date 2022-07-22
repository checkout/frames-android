package com.checkout.buildsrc.publishing

import groovy.namespace.QName
import groovy.util.Node
import org.gradle.api.publish.maven.MavenPom

object MavenPublishingHelper {

    fun updatePom(mavenPom: MavenPom, publishComponentInformation: PublishComponentInformation) {
        mavenPom.apply {
            description.set(publishComponentInformation.pomDescription)
            name.set(publishComponentInformation.pomName)
            url.set(publishComponentInformation.githubProjectUrl)

            licenses {
                license {
                    name.set(publishComponentInformation.licenseName)
                    url.set("https://raw.githubusercontent.com/${publishComponentInformation.githubProjectName}/master/LICENSE")
                }
            }
            developers {
                developer {
                    id.set(publishComponentInformation.developerId)
                    name.set(publishComponentInformation.developerName)
                }
            }

            scm {
                connection.set("scm:git:${publishComponentInformation.githubProjectUrl}.git")
                developerConnection.set("scm:git:${publishComponentInformation.githubProjectUrl}.git")
                url.set(publishComponentInformation.githubProjectUrl)
            }

            withXml {
                asNode().getNode("dependencies")?.run {
                    getNodes("dependency").forEach { node ->
                        if (isExcluded(node, publishComponentInformation.groupId)) {
                            remove(node)
                        }
                    }
                }
            }
        }
    }

    private fun isExcluded(node: Node, groupId: String): Boolean {
        return node.getNode("groupId")
            ?.value()
            ?.toString()
            ?.contains(groupId) ?: false
    }

    private fun Node.getNode(name: String) = getAt(QName("*", name)).firstOrNull() as? Node

    private fun Node.getNodes(name: String) = getAt(QName("*", name)).mapNotNull { it as? Node }

}