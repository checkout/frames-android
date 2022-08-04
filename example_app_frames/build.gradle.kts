import com.checkout.buildsrc.applyDeclarativeUIConfigurations
import com.checkout.buildsrc.commonDependencies
import com.checkout.buildsrc.genericAndroidTestDependencies
import com.checkout.buildsrc.genericTestDependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.checkout.example_app_frames"
        minSdk = 28
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler_ext
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    applyDeclarativeUIConfigurations()
    commonDependencies()
    genericTestDependencies()
    genericAndroidTestDependencies()
    implementation(project(":checkout"))
    implementation("androidx.activity:activity-compose:${Versions.compose_version}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}")
}
