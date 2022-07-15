package com.checkout.buildsrc

import Dependencies
import com.checkout.buildsrc.utils.testImplementation
import org.gradle.api.Project

fun Project.applyAndroidJUnit4Configuration() {
    dependencies.apply {
        testImplementation(Dependencies.junit4)
        testImplementation(Dependencies.junitVintageEngine)
        testImplementation(Dependencies.robolectric)
    }
}
