@file:Suppress("UnstableApiUsage")

include(":app", ":checkout", ":frames",":example_app_frames")

/**
 * Setup dependencies for all projects
 */
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}
