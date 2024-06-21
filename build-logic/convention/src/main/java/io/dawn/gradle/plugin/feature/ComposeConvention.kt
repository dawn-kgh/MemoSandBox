package io.dawn.gradle.plugin.feature

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.LibraryExtension
import io.dawn.gradle.plugin.implementation
import io.dawn.gradle.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import java.io.File
import kotlin.jvm.optionals.getOrNull

class ComposeConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                libs.findPlugin("compose.compiler").getOrNull()?.get()?.pluginId?.let {
                    apply(it)
                }
            }

            extensions.findByType(LibraryExtension::class.java)?.let {
                extensions.configure<LibraryExtension> {
                    configureCompose()
                }
            }

            extensions.findByType(ApplicationExtension::class.java)?.let {
                extensions.configure<ApplicationExtension> {
                    configureCompose()
                }
            }

            dependencies {
                libs.implementation("app.cash.molecule")
                libs.implementation("androidx.compose.foundation")
                libs.implementation("androidx.compose.runtime")
                libs.implementation("androidx.compose.ui")
                libs.implementation("androidx.compose.ui.tooling")
                libs.implementation("androidx.compose.ui.tooling.preview")
                libs.implementation("androidx.compose.material3")
                libs.implementation("androidx.activity.compose")
            }
        }
    }


    context(Project)
    private fun configureCompose() {
        extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
            enableStrongSkippingMode.set(true)
            includeSourceInformation.set(true)
            reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
            metricsDestination.set(layout.buildDirectory.dir("compose_compiler"))
            stabilityConfigurationFile.set(File("${rootProject.projectDir}/compose/compose_compiler_config.conf"))
        }
    }
}