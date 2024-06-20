package io.dawn.gradle.plugin.module

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppExtension
import io.dawn.gradle.plugin.configVersionAndSdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get

class ApplicationConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("io.dawn.compose")
                apply("io.dawn.hilt")
            }

            configure<ApplicationExtension> {
                namespace = "io.dawn.sandbox"

                configBuildFeature()
                configBundleSplit()
                configSourceSets()
                configPackaging()
            }

            configure<AppExtension> {
                configDefaultConfig()
                configVersionAndSdk()
            }
        }
    }
}

context(Project)
private fun AppExtension.configDefaultConfig() {
    defaultConfig {
        applicationId = "io.dawn.sandbox"
        versionCode = 1
        versionName = "1.0.0"
    }
}

context(Project)
private fun ApplicationExtension.configBundleSplit() {
    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }
}

context(Project)
private fun ApplicationExtension.configBuildFeature() {
    buildFeatures {
        compose = true
        viewBinding = false
        dataBinding = false
        aidl = false
        buildConfig = true
    }
}

context(Project)
private fun ApplicationExtension.configSourceSets() {
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java")
        }
        getByName("test") {
            java.srcDirs("src/test/java")
        }
    }
}

context(Project)
private fun ApplicationExtension.configPackaging() {
    packaging {
        jniLibs.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt",
                "META-INF/services/javax.annotation.processing.Processor",
                "DebugProbesKt.bin"
            )
        )
    }
}