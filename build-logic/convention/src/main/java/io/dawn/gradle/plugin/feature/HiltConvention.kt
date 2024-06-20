package io.dawn.gradle.plugin.feature

import io.dawn.gradle.plugin.applyKsp
import io.dawn.gradle.plugin.implementation
import io.dawn.gradle.plugin.ksp
import io.dawn.gradle.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyKsp()
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                libs.implementation("hilt.android")
                libs.ksp("hilt.compiler")
            }
        }
    }
}