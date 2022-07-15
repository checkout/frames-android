package com.checkout.buildsrc.publishing

data class PublishComponentInformation(
    val groupId: String,
    val artifactId: String,
    val version: String,

    val pomName: String,
    val pomDescription: String,
    val githubProjectName: String,
    val githubProjectUrl: String,

    val developerId: String,
    val developerName: String,

    val licenseName: String, // Example "MIT License
) {

    val isSnapshotRelease: Boolean
        get() = version.endsWith("SNAPSHOT", ignoreCase = true)
}