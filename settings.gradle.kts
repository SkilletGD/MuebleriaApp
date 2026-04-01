rootProject.name = "KotlinProject"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google ()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/releases/")
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google ()
        mavenCentral()
    }
}

include(":composeApp")