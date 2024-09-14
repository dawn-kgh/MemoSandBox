package io.dawn.gradle.plugin.module

import com.android.build.gradle.LibraryExtension
import io.dawn.gradle.plugin.configVersionAndSdk
import io.dawn.gradle.plugin.implementation
import io.dawn.gradle.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class DataConvention : Plugin<Project> {
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
                libs.implementation("firebase.crashlytics.ktx")
                libs.implementation("firebase.messaging.ktx")

                libs.implementation("kotlinx.coroutine.core")
                libs.implementation("kotlinx.coroutine.android")
                libs.implementation("kotlinx.serialization")

                libs.implementation("androidx.datastore")
            }
        }
    }
}