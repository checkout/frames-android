package com.checkout.buildsrc

import Dependencies
import com.checkout.buildsrc.utils.androidTestImplementation
import com.checkout.buildsrc.utils.implementation
import com.checkout.buildsrc.utils.testImplementation
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Dependencies always required for Android modules
 */
fun DependencyHandler.commonDependencies() {
    implementation(Dependencies.kotlinCoroutines)
    implementation(Dependencies.kotlinCoroutinesAndroid)
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appcompat)
}

/**
 * Dependencies usually required for modules with a ui
 */
fun DependencyHandler.androidUi() {
    implementation(Dependencies.constraintLayout)
}

/**
 * Networking dependencies
 */
fun DependencyHandler.networkingDependencies() {
    implementation(Dependencies.okhttp)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.moshi)
}

/**
 * Logging dependencies
 */
fun DependencyHandler.logging() {
    implementation(Dependencies.eventLogger)
}

/**
 * Material design dependencies
 */
fun DependencyHandler.materialDesign() {
    implementation(Dependencies.materialDesign)
}

/**
 * Generic instrumented test related dependencies
 */
fun DependencyHandler.genericTestDependencies() {
    testImplementation(Dependencies.okhttpMockServer)
    testImplementation(Dependencies.kotlinCoroutinesTest)
    testImplementation(Dependencies.jsonTest)
}

/**
 * Generic instrumented test related dependencies
 */
fun DependencyHandler.genericAndroidTestDependencies() {
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.androidxJunitKtx)
    androidTestImplementation(Dependencies.androidxTestRunner)
    androidTestImplementation(Dependencies.androidxTestCore)
    androidTestImplementation(Dependencies.androidxTestCoreKtx)
    androidTestImplementation(Dependencies.androidxTestRules)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(Dependencies.eventLogger)
}

fun DependencyHandler.mockk() {
    testImplementation(Dependencies.mockk)
    androidTestImplementation(Dependencies.mockkAndroid)
}

fun DependencyHandler.kluent() {
    testImplementation(Dependencies.kluent)
    androidTestImplementation(Dependencies.kluentAndroid)
}