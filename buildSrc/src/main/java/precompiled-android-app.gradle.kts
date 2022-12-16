@file:Suppress("UnstableApiUsage")

import com.checkout.buildsrc.applyDeclarativeUIConfigurations
import com.checkout.buildsrc.commonDependencies
import com.checkout.buildsrc.genericAndroidTestDependencies
import com.checkout.buildsrc.genericTestDependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = AndroidConfig.compileSdk
    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        testInstrumentationRunner = AndroidConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler_ext
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
