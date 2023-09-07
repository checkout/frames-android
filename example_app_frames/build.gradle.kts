plugins {
    id("precompiled-android-app")
}

android.namespace = "com.checkout.example.frames"

android {
    defaultConfig {
        applicationId = ExampleAppFramesConfig.id
        versionCode = ExampleAppFramesConfig.versionCode
        versionName = ExampleAppFramesConfig.versionName
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
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
    implementation(FramesConfig.framesAndroidDependency)
}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        if (FramesConfig.useLocalModuleDependencies) {

            substitute(module("${FramesConfig.productGroupId}:${FramesConfig.productArtefactId}"))
                .using(project(":frames"))
                .because("Development and QA validation is performed using the local project module")
        }
    }
}
