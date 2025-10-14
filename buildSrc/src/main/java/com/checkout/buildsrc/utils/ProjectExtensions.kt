package com.checkout.buildsrc.utils

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

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
