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
                apply("kakao.wheel.ComposeConvention")
                apply("kakao.wheel.HiltConvention")
            }

            configure<LibraryExtension> {
                configVersionAndSdk()
                configBuildFeatures()

                buildFeatures {
                    viewBinding = true
                    dataBinding = true
                }
            }

            dependencies {
                implementation(project(":meteor:library:base"))
                implementation(project(":meteor:userinterface:base"))
                implementation(project(":meteor:userinterface:design"))
                implementation(project(":domain:base"))
                implementation(project(":domain:root"))
                implementation(project(":data:call-api"))
                implementation(project(":data:common-api"))
                implementation(project(":data:location-api"))
                implementation(project(":data:driver-api"))
                implementation(project(":data:connection-api"))
                implementation(project(":data:driving:api"))
                implementation(project(":core:resource"))

                implementation(project(":core:network:api"))
                implementation(project(":core:base:api"))
                implementation(project(":core:coroutine:api"))
                implementation(project(":meteor:userinterface:camera"))
                implementation(project(":meteor:userinterface:callcard"))
                testImplementation(project(":core:test"))

                libs.bundleImplementation("android.library.common")
            }
        }
    }
}