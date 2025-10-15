@file:Suppress("UnstableApiUsage")

import com.checkout.buildsrc.applyDeclarativeUIConfigurations
import com.checkout.buildsrc.commonDependencies
import com.checkout.buildsrc.genericAndroidTestDependencies
import com.checkout.buildsrc.genericTestDependencies
import com.checkout.buildsrc.utils.android
import com.checkout.buildsrc.utils.implementation
import com.checkout.buildsrc.utils.kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)
    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        testInstrumentationRunner = AndroidConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

dependencies {
    applyDeclarativeUIConfigurations()
    commonDependencies()
    genericTestDependencies()
    genericAndroidTestDependencies()
    implementation(Dependencies.compose_activity)
    implementation(Dependencies.lifeCycleRunTime)
}
