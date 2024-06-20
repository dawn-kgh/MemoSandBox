plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.6.0"
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include(":convention")

rootProject.name = "build-logic"
