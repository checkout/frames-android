package com.checkout.buildsrc

import AndroidConfig
import Dependencies
import com.android.build.gradle.BaseExtension
import com.checkout.buildsrc.utils.androidTestImplementation
import com.checkout.buildsrc.utils.androidTestRuntimeOnly
import com.checkout.buildsrc.utils.testImplementation
import com.checkout.buildsrc.utils.testRuntimeOnly
import de.mannodermaus.gradle.plugins.junit5.dsl.AndroidJUnitPlatformExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

fun Project.applyAndroidJUnit5Configuration() {
    apply(plugin = "de.mannodermaus.android-junit5")

    extensions.getByType<BaseExtension>().apply {
        defaultConfig.apply {
            testInstrumentationRunnerArguments += AndroidConfig.junit5testInstrumentationRunnerArguments
        }

        testOptions {
            junitPlatform {
                // Don't raise errors about incorrect configuration of JUnit 5 instrumentation tests
                instrumentationTests.integrityCheckEnabled = false
                // Avoid junit5 interfering the [JacocoTestReport] task
                jacocoOptions.taskGenerationEnabled = false
            }
        }
    }


    dependencies.apply {
        junit5()
        androidJunit5()
    }
}

private val Project.junitPlatform: AndroidJUnitPlatformExtension
    get() =
        (this as org.gradle.api.plugins.ExtensionAware).extensions.getByName("junitPlatform") as AndroidJUnitPlatformExtension

private fun DependencyHandler.junit5() {
    testImplementation(Dependencies.junitJupiterApi)
    testRuntimeOnly(Dependencies.junitJupiterEngine)
    testImplementation(Dependencies.junitJupiterParams)
}

private fun DependencyHandler.androidJunit5() {
    androidTestImplementation(Dependencies.junitJupiterApi)
    androidTestImplementation(Dependencies.junitJupiterParams)
    androidTestImplementation(Dependencies.mannodermausAndroidJunitCore)
    androidTestRuntimeOnly(Dependencies.mannodermausAndroidTestRunner)
}
