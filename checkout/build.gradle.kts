import com.checkout.buildsrc.applyAndroidJUnit5Configuration
import com.checkout.buildsrc.applyCommonLibConfigurations
import com.checkout.buildsrc.applyAndroidJUnit4Configuration
import com.checkout.buildsrc.applyNetworkConfigurations
import com.checkout.buildsrc.BuildConfigFieldName
import com.checkout.buildsrc.publishing.configureMavenPublication
import com.checkout.buildsrc.publishing.PublishComponentInformation

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("org.jetbrains.dokka")
}

configureMavenPublication("CheckoutAndroid") {
    with(CheckoutConfig) {
        PublishComponentInformation(
            groupId,
            artifactId,
            version,
            pomName,
            pomDescription,
            githubProjectName,
            githubProjectUrl,
            AppConfig.developerId,
            AppConfig.developerName,
            "" // TODO Confirm License Name
        )
    }
}
applyAndroidJUnit5Configuration()
applyAndroidJUnit4Configuration()
applyCommonLibConfigurations()
applyNetworkConfigurations()

android {
    resourcePrefix = "cko_"

    defaultConfig {
        buildConfigField(
            "String",
            BuildConfigFieldName.productVersion,
            "\"${CheckoutConfig.version}\""
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.productName,
            "\"${CheckoutConfig.pomName}\""
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.productIdentifier,
            "\"${CheckoutConfig.groupId}.${CheckoutConfig.artifactId}\""
        )

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            buildConfigField(
                "Boolean",
                BuildConfigFieldName.defaultLogcatMonitoring,
                "false"
            )
        }

        debug {
            buildConfigField(
                "Boolean",
                BuildConfigFieldName.defaultLogcatMonitoring,
                "true"
            )
        }
    }
}

dependencies {
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
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
