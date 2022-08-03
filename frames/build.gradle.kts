import com.checkout.buildsrc.applyAndroidJUnit4Configuration
import com.checkout.buildsrc.applyAndroidJUnit5Configuration
import com.checkout.buildsrc.applyCommonLibConfigurations
import com.checkout.buildsrc.applyDeclarativeUIConfigurations

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

applyAndroidJUnit5Configuration()
applyAndroidJUnit4Configuration()
applyCommonLibConfigurations()
applyDeclarativeUIConfigurations()

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler_ext
    }
}

dependencies {
    implementation(project(":checkout"))
}
