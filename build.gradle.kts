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

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
