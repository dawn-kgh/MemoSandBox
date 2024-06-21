package io.dawn.gradle.plugin.feature

import io.dawn.gradle.plugin.androidTestImplementation
import io.dawn.gradle.plugin.libs
import io.dawn.gradle.plugin.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                libs.testImplementation("junit")
                libs.testImplementation("mockk.compose.runtime")

                libs.androidTestImplementation("androidx.junit")
                libs.androidTestImplementation("mockk.compose.runtime")
            }
        }
    }
}