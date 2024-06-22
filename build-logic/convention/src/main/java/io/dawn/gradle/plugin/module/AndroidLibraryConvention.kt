package io.dawn.gradle.plugin.module

import com.android.build.gradle.LibraryExtension
import io.dawn.gradle.plugin.bundleImplementation
import io.dawn.gradle.plugin.configBuildFeatures
import io.dawn.gradle.plugin.configVersionAndSdk
import io.dawn.gradle.plugin.implementation
import io.dawn.gradle.plugin.libs
import io.dawn.gradle.plugin.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("io.dawn.compose")
                apply("io.dawn.hilt")
                apply("io.dawn.test")
            }

            configure<LibraryExtension> {
                configVersionAndSdk()
                configBuildFeatures()
            }

            dependencies {
                implementation(project(":core:design"))
            }
        }
    }
}