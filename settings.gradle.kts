@file:Suppress("UnstableApiUsage")

include(":app", ":android-sdk", ":checkout", ":frames", ":FramesKotlinSample", ":examples_app", ":googlepay_examples_app")
project(":examples_app").projectDir = file("demos/examples/app")
project(":googlepay_examples_app").projectDir = file("demos/googlepay_example/app")

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
