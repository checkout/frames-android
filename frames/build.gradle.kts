import com.checkout.buildsrc.applyAndroidJUnit4Configuration
import com.checkout.buildsrc.applyAndroidJUnit5Configuration
import com.checkout.buildsrc.applyCommonLibConfigurations
import com.checkout.buildsrc.applyDeclarativeUIConfigurations
import com.checkout.buildsrc.applyDIConfigurations

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

applyAndroidJUnit5Configuration()
applyAndroidJUnit4Configuration()
applyCommonLibConfigurations()
applyDeclarativeUIConfigurations()
applyDIConfigurations()

android {
    resourcePrefix = "cko_"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_compiler_ext
    }

    kotlinOptions {
        // Needed to prevent warning when ViewModelProvider.Factory inherited
        // "Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option"
        freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all-compatibility"
    }
}

dependencies {
    implementation(project(":checkout"))
}
