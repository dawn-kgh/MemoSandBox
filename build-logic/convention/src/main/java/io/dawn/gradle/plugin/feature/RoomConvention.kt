package io.dawn.gradle.plugin.feature

import androidx.room.gradle.RoomExtension
import io.dawn.gradle.plugin.applyKsp
import io.dawn.gradle.plugin.implementation
import io.dawn.gradle.plugin.ksp
import io.dawn.gradle.plugin.libs
import io.dawn.gradle.plugin.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomConvention: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyKsp {
                arg("room.schemaLocation", "${projectDir}/schemas")
            }
            with(pluginManager) {
                apply("androidx.room")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("${projectDir}/schemas")
            }

            dependencies {
                libs.implementation("androidx.room.runtime")
                libs.ksp("androidx.room.compiler")
                libs.testImplementation("androidx.room.test")
            }
        }
    }
}