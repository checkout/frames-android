import com.vanniktech.code.quality.tools.CodeQualityToolsPluginExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath(ProjectDependencies.androidGradlePlugin)
        classpath(ProjectDependencies.kotlinGradlePlugin)
        classpath(ProjectDependencies.codeQualityToolsPlugin)
        classpath(ProjectDependencies.dokkaPlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        val githubEventLoggerPackageUrl =
            project.findProperty("githubEventLoggerPackageUrl") as? String
                ?: System.getenv("githubEventLoggerPackageUrl")
        val githubPackagesUser = project.findProperty("githubPackagesUser") as? String
            ?: System.getenv("githubPackagesUser")
        val githubPackagesToken = project.findProperty("githubPackagesToken") as? String
            ?: System.getenv("githubPackagesToken")
        if (githubEventLoggerPackageUrl != null) {
            maven {
                name = "GitHubPackages"
                url = uri(githubEventLoggerPackageUrl)
                credentials {
                    username = githubPackagesUser
                    password = githubPackagesToken
                }
            }
        }
    }
}

apply(plugin = ProjectDependencies.codeQualityTools)

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

configure<CodeQualityToolsPluginExtension> {
    failEarly = true
    xmlReports = false
    htmlReports = true
    textReports = false
    ignoreProjects = listOf("buildSrc", "app", "android-sdk", "FramesKotlinSample", "examples_app", "googlepay_examples_app")

    checkstyle {
        enabled = false
    }
    pmd {
        enabled = false
    }
    lint {
        enabled = true
        baselineFileName = "lint-baseline.xml"
    }
    ktlint {
        enabled = true
        toolVersion = Versions.ktlint
    }
    detekt {
        enabled = true
        toolVersion = Versions.detect
        config = "code_quality_tools/detekt.yml"
        failFast = true
    }
    cpd {
        enabled = false
    }
    kotlin {
        allWarningsAsErrors = true
    }
}
