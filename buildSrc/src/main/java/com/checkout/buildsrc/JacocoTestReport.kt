package com.checkout.buildsrc

import Versions
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

// Setup [JacocoTestReport] task to generate the test coverage report.
fun Project.applyJacocoTestReport() {
    plugins.apply("jacoco")

    extensions.getByType(JacocoPluginExtension::class.java).apply {
        version = Versions.jacoco
    }

    tasks.withType(Test::class.java) {
        configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }

    tasks.register("jacocoTestReport", JacocoReport::class.java) {
        dependsOn("testDebugUnitTest")

        reports {
            xml.required.set(true)
            html.required.set(true)
        }
        val buildDir = project.buildDir
        val javaTree = fileTree("$buildDir/intermediates/javac/debug/classes") { setExcludes(FILE_FILTER) }
        val kotlinTree = fileTree("$buildDir/tmp/kotlin-classes/debug") { setExcludes(FILE_FILTER) }
        val execSrc = fileTree(buildDir) { setIncludes(listOf("**/*.exec")) }
        sourceDirectories.setFrom(files("${project.projectDir}/src/main/java"))
        classDirectories.setFrom(files(javaTree, kotlinTree))
        executionData.setFrom(execSrc)
    }
}

private val FILE_FILTER by lazy {
    listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "**/androidTest/**",
    )
}
