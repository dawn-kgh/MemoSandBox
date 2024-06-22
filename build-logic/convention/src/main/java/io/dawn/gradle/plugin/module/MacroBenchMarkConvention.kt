package io.dawn.gradle.plugin.module

import com.android.build.gradle.TestExtension
import io.dawn.gradle.plugin.configVersionAndSdk
import io.dawn.gradle.plugin.implementation
import io.dawn.gradle.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class MacroBenchMarkConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("org.jetbrains.kotlin.android")
            }

            configure<TestExtension> {
                configVersionAndSdk()

                namespace = "io.dawn.macrobenchmark"

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildFeatures {
                    buildConfig = true
                }

                buildTypes {
                    create("benchmark") {
                        isDebuggable = true
                        signingConfig = signingConfigs.getByName("debug")
                        matchingFallbacks.add("release")
                    }
                }

                flavorDimensions.add("stage")
                productFlavors {
                    create("alpha") {
                        dimension = "stage"
                    }

                    create("production") {
                        dimension = "stage"
                    }
                }

                targetProjectPath = ":app"
                experimentalProperties["android.experimental.self-instrumenting"] = true
            }

            dependencies {
                implementation(project(":core:test"))

                libs.implementation("junit")
                libs.implementation("test.espresso.core")
                libs.implementation("androidx.test.uiautomator")
                libs.implementation("androidx.benchmark.macro.junit")
                libs.implementation("androidx.profileinstaller")
            }
        }
    }
}