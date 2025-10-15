package com.checkout.buildsrc.utils

import org.gradle.api.artifacts.dsl.DependencyHandler

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandler.ksp(dependency: String) {
    add("ksp", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: String) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.androidTestRuntimeOnly(dependency: String) {
    add("androidTestRuntimeOnly", dependency)
}

fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandler.testRuntimeOnly(dependency: String) {
    add("testRuntimeOnly", dependency)
}

fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}
