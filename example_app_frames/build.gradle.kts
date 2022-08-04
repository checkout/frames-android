plugins {
    id("precompiled-android-app")
}

android {
    defaultConfig {
        applicationId = AppConfig.ExampleAppFrames.id
        versionCode = AppConfig.ExampleAppFrames.versionCode
        versionName = AppConfig.ExampleAppFrames.versionName
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":checkout"))
}
