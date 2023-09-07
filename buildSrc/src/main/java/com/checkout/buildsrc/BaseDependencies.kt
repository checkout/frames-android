package com.checkout.buildsrc

import Dependencies
import com.checkout.buildsrc.utils.androidTestImplementation
import com.checkout.buildsrc.utils.debugImplementation
import com.checkout.buildsrc.utils.implementation
import com.checkout.buildsrc.utils.kapt
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
 * Dependencies usually required for modules with a Imperative UI
 */
fun DependencyHandler.androidImperativeUI() {
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.materialDesign)
}

/**
 * Dependencies usually required for modules with a Declarative UI
 */
fun DependencyHandler.androidDeclarativeUI() {
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_tooling_preview)
    implementation(Dependencies.compose_material3)
    implementation(Dependencies.lifecycle_viewmodel)
    implementation(Dependencies.compose_viewmodel)
    implementation(Dependencies.compose_navigation)
    implementation(Dependencies.compose_navigation_accompanist)
    implementation(Dependencies.flow_layout)
    debugImplementation(Dependencies.compose_ui_tooling)
    debugImplementation(Dependencies.compose_ui_test_manifest)
    debugImplementation(Dependencies.compose_customview_poolingcontainer)
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
 * Dependency injection
 */
fun DependencyHandler.dependencyInjection() {
    implementation(Dependencies.dagger)
    implementation(Dependencies.dagger_support)
    kapt(Dependencies.dagger_processor)
    kapt(Dependencies.dagger_compiler)
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

fun DependencyHandler.declarativeUITestDependencies() {
    androidTestImplementation(Dependencies.compose_ui_test)
}

fun DependencyHandler.mockk() {
    testImplementation(Dependencies.mockk)
    androidTestImplementation(Dependencies.mockkAndroid)
}

fun DependencyHandler.kluent() {
    testImplementation(Dependencies.kluent)
    androidTestImplementation(Dependencies.kluentAndroid)
}
