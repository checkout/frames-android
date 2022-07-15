plugins {
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.property("kotlinVersion")}")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:${project.property("mannodermausAndroidJunit5Version")}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${project.property("dokkaVersion")}")

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
}