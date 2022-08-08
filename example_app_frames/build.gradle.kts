plugins {
    id("precompiled-android-app")
}

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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":checkout"))
}
