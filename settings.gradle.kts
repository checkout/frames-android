@file:Suppress("UnstableApiUsage")

include(":app", ":checkout", ":frames",":example_app_frames")

/**
 * Setup dependencies for all projects
 */
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.fpregistry.io/releases") }
    }
}
