@file:Suppress("UnstableApiUsage")

include(":app", ":android-sdk", ":FramesKotlinSample", ":examples_app", ":googlepay_examples_app", ":checkout")
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