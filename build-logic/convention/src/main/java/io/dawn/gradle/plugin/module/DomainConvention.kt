package io.dawn.gradle.plugin.module

import com.android.build.gradle.LibraryExtension
import io.dawn.gradle.plugin.configVersionAndSdk
import io.dawn.gradle.plugin.implementation
import io.dawn.gradle.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class DomainConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
                apply("kotlinx-serialization")
                apply("io.dawn.hilt")
            }

            configure<LibraryExtension> {
                configVersionAndSdk()
            }

            dependencies {
                implementation(project(":core:base:api"))
                implementation(project(":core:coroutine:api"))
                implementation(project(":core:network:api"))

                libs.implementation("kotlinx.coroutine.android")
            }
        }
    }
}