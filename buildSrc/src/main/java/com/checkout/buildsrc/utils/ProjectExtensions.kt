package com.checkout.buildsrc.utils

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal fun Project.getProperty(property: String): String? =
    project.findProperty(property) as String? ?: System.getenv(property)

internal fun Project.getRequiredProperty(property: String): String {
    val propertyValue = getProperty(property)
    check(!propertyValue.isNullOrEmpty()) { "Required property $property is not defined" }
    return propertyValue
}

/**
 * Configures the [kotlin][org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension] extension.
 */
internal fun Project.kotlin(configure: KotlinAndroidProjectExtension.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlin", configure)
}

/**
 * Configures the [kotlin][org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension] extension.
 */
internal fun Project.android(configure: BaseExtension.() -> Unit) {
    val androidExtension = ((this as ExtensionAware).extensions).let {
        it.findByType(LibraryExtension::class.java) ?: it.findByType(AppExtension::class.java)
    }
    check(androidExtension is BaseExtension) { "Unable to access Android project extensions" }
    androidExtension.apply(configure)
}

/**
 * Configures the [kotlinOptions][org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions] extension.
 */
internal fun BaseExtension.kotlinOptions(configure: KotlinJvmOptions.() -> Unit): Unit =
    (this as ExtensionAware).extensions.configure("kotlinOptions", configure)
