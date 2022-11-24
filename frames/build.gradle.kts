import com.checkout.buildsrc.applyAndroidJUnit4Configuration
import com.checkout.buildsrc.applyAndroidJUnit5Configuration
import com.checkout.buildsrc.applyCommonLibConfigurations
import com.checkout.buildsrc.applyDeclarativeUIConfigurations
import com.checkout.buildsrc.applyDIConfigurations
import com.checkout.buildsrc.BuildConfigFieldName
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("org.jetbrains.dokka")
}

applyAndroidJUnit5Configuration()
applyAndroidJUnit4Configuration()
applyCommonLibConfigurations()
applyDeclarativeUIConfigurations()
applyDIConfigurations()

android {
    resourcePrefix = "cko_"

    defaultConfig {
        buildConfigField(
            "String",
            BuildConfigFieldName.productVersion,
            "\"${FramesConfig.productVersion}\""
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.productName,
            "\"${FramesConfig.pomName}\""
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.productIdentifier,
            "\"${FramesConfig.productGroupId}.${FramesConfig.productArtefactId}\""
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.loggingIdentifier,
            "\"${FramesConfig.loggingGroupId}.${FramesConfig.productArtefactId}\""
        )

        consumerProguardFiles("consumer-rules.pro")
    }

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
    api(project(":checkout"))
}

tasks.withType<DokkaTask>().configureEach {
    moduleName.set(FramesConfig.docsTitle)
    moduleVersion.set(FramesConfig.productVersion)
    suppressInheritedMembers.set(true)
    suppressObviousFunctions.set(true)

    dokkaSourceSets {
        configureEach {
            suppress.set(true)
        }

        named("main") {
            suppress.set(false)
            noAndroidSdkLink.set(false)

            displayName.set(FramesConfig.docsDisplayName)
            skipEmptyPackages.set(true)
            suppress.set(false)
            reportUndocumented.set(true)

            includes.from("module.md")
        }
    }
}
