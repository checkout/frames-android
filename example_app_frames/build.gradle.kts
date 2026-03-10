import java.util.Properties

plugins {
    id("precompiled-android-app")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.checkout.example.frames"
    defaultConfig {
        applicationId = ExampleAppFramesConfig.id
        versionCode = ExampleAppFramesConfig.versionCode
        versionName = ExampleAppFramesConfig.versionName

        Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }.run {
            buildConfigField(
                "String",
                "SANDBOX_PUBLIC_KEY",
                "\"${this["sandbox.components.public_key"]}\"",
            )
            buildConfigField(
                "String",
                "SANDBOX_SECRET_KEY",
                "\"${this["sandbox.components.secret_key"]}\"",
            )
            buildConfigField(
                "String",
                "SANDBOX_PROCESSING_CHANNEL_ID",
                "\"${this["sandbox.components.processing_channel_id"]}\"",
            )
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(FramesConfig.framesAndroidDependency)
}

configurations.configureEach {
    resolutionStrategy.dependencySubstitution {
        if (FramesConfig.useLocalModuleDependencies) {

            substitute(module("${FramesConfig.productGroupId}:${FramesConfig.productArtefactId}"))
                .using(project(":frames"))
                .because("Development and QA validation is performed using the local project module")
        }
    }
}
