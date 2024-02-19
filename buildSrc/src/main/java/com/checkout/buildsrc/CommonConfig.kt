package com.checkout.buildsrc

import AndroidConfig
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.checkout.buildsrc.utils.android
import com.checkout.buildsrc.utils.kotlin
import com.checkout.buildsrc.utils.kotlinOptions
import org.gradle.api.JavaVersion
import org.gradle.api.Project

fun Project.applyCommonConfigurations() {
    android {
        compileSdkVersion(AndroidConfig.compileSdk)

        defaultConfig {
            minSdk = AndroidConfig.minSdk
            targetSdk = AndroidConfig.targetSdk

            testInstrumentationRunner = AndroidConfig.androidTestInstrumentation
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
            unitTests.isIncludeAndroidResources = true
        }
    }

    dependencies.apply {
        commonDependencies()
        genericTestDependencies()
        genericAndroidTestDependencies()
        logging()

        mockk()
        kluent()
    }
}

fun Project.applyNetworkConfigurations() {
    android {
        dependencies.apply {
            networkingDependencies()
        }
    }
}

fun Project.applyRiskSdkConfigurations() {
    android {
        dependencies.apply {
            riskSdkDependencies()
        }
    }
}

fun Project.applyDeclarativeUIConfigurations() {
    android {
        dependencies.apply {
            androidDeclarativeUI()
            declarativeUITestDependencies()
        }
    }
}

fun Project.applyDIConfigurations() {
    android {
        dependencies.apply {
            dependencyInjection()
        }
    }
}

fun Project.applyCommonAppConfigurations() {
    android {
        require(this is AppExtension) {
            "Android application plugin has not been applied"
        }

        applyCommonConfigurations()

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
            getByName("debug") {
                isMinifyEnabled = false
            }
        }

        buildFeatures.apply {
            viewBinding = true
        }
    }
}

fun Project.applyCommonLibConfigurations() {
    android {
        require(this is LibraryExtension) {
            "Android library plugin has not been applied"
        }

        lint {
            abortOnError = true
        }

        applyCommonConfigurations()

        buildTypes {
            release {
                isMinifyEnabled = false
            }
            debug {
                isMinifyEnabled = false
            }
        }

        buildFeatures.apply {
            buildConfig = true
        }

        kotlin {
            explicitApi()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs +
                "-opt-in=kotlin.contracts.ExperimentalContracts" +
                "-Xexplicit-api=strict"
        }

        packagingOptions.resources {
            // excludes += "META-INF/*.kotlin_module" // Excluding metadata will break support for Kotlin extensions
            excludes +=
                listOf(
                    "META-INF/LICENSE*",
                    "META-INF/AL*",
                    "META-INF/LGPL*",
                    "**/attach_hotspot_windows.dll",
                )
        }
    }
}
