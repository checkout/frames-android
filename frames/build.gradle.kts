import com.checkout.buildsrc.BuildConfigFieldName
import com.checkout.buildsrc.applyAndroidJUnit4Configuration
import com.checkout.buildsrc.applyAndroidJUnit5Configuration
import com.checkout.buildsrc.applyCommonLibConfigurations
import com.checkout.buildsrc.applyDIConfigurations
import com.checkout.buildsrc.applyDeclarativeUIConfigurations
import com.checkout.buildsrc.applyJacocoTestReport
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.dokka")
    id("maven-publish")
}

applyAndroidJUnit5Configuration()
applyAndroidJUnit4Configuration()
applyCommonLibConfigurations()
applyDeclarativeUIConfigurations()
applyDIConfigurations()
applyJacocoTestReport()

android {
    resourcePrefix = "cko_"
    namespace = "com.checkout.frames"

    defaultConfig {
        buildConfigField(
            "String",
            BuildConfigFieldName.productVersion,
            "\"${FramesConfig.productVersion}\"",
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.productName,
            "\"${FramesConfig.pomName}\"",
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.productIdentifier,
            "\"${FramesConfig.productGroupId}.${FramesConfig.productArtefactId}\"",
        )

        buildConfigField(
            "String",
            BuildConfigFieldName.loggingIdentifier,
            "\"${FramesConfig.loggingGroupId}.${FramesConfig.productArtefactId}\"",
        )

        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
    }
    publishing {
        singleVariant("release") {}
    }
    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    kotlin {
        compilerOptions {
            // Needed to prevent warning when ViewModelProvider.Factory inherited
            // "Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option"
            freeCompilerArgs.addAll(
                "-Xjvm-default=all-compatibility",
                "-Xannotation-default-target=param-property",
            )
        }
    }
}

dependencies {
    api(project(":checkout"))
}

/* Documentation */
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

/* Release process */

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components.getByName("release"))
                artifactId = FramesConfig.productArtefactId
                version = FramesConfig.productVersion
                groupId = FramesConfig.productGroupId

                pom {
                    name.set(FramesConfig.pomName)
                    description.set(FramesConfig.pomDescription)
                    url.set(FramesConfig.githubProjectUrl)

                    licenses {
                        license {
                            name.set(AppConfig.pomLicenseName)
                            url.set(FramesConfig.pomLicenseUrl)
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
