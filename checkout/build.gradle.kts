import com.checkout.buildsrc.applyAndroidJUnit5Configuration
import com.checkout.buildsrc.applyCommonLibConfigurations
import com.checkout.buildsrc.applyAndroidJUnit4Configuration
import com.checkout.buildsrc.applyNetworkConfigurations
import com.checkout.buildsrc.BuildConfigFieldName
import com.checkout.buildsrc.applyJacocoTestReport

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("org.jetbrains.dokka")
    id("maven-publish")
}

applyAndroidJUnit5Configuration()
applyAndroidJUnit4Configuration()
applyCommonLibConfigurations()
applyNetworkConfigurations()
applyJacocoTestReport()

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
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")

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

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components.getByName("release"))
                artifactId = CheckoutConfig.artifactId
                version = CheckoutConfig.version
                groupId = FramesConfig.productGroupId

                pom {
                    name.set(CheckoutConfig.pomName)
                    description.set(CheckoutConfig.pomDescription)
                    url.set(CheckoutConfig.githubProjectUrl)

                    licenses {
                        license {
                            name.set(AppConfig.pomLicenseName)
                            url.set(CheckoutConfig.pomLicenseUrl)
                        }
                    }
                    developers {
                        developer {
                            id.set(AppConfig.pomDeveloperId)
                            name.set(AppConfig.pomDeveloperName)
                        }
                    }
                }
            }
        }
    }
}
