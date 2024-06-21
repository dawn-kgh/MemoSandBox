import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.buildscript.android.gradle)
    compileOnly(libs.buildscript.kotlin.gradle)
    compileOnly(libs.buildscript.google.ksp)
    compileOnly(libs.buildscript.compose.compile.extension)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
    }
}

gradlePlugin {
    plugins {
        register("io.dawn.app") {
            id = "io.dawn.app"
            implementationClass = "io.dawn.gradle.plugin.module.ApplicationConvention"
        }

        register("io.dawn.presentation") {
            id = "io.dawn.presentation"
            implementationClass = "io.dawn.gradle.plugin.module.AndroidLibraryConvention"
        }

        register("io.dawn.core") {
            id = "io.dawn.core"
            implementationClass = "io.dawn.gradle.plugin.module.CoreConvention"
        }
      /*  register("io.dawn.domain") {
            id = "io.dawn.DomainModule"
            implementationClass = "io.dawn.gradle.plugin.module.DomainConvention"
        }

        register("io.dawn.data") {
            id = "io.dawn.DataModule"
            implementationClass = "io.dawn.gradle.plugin.module.DataConvention"
        }

        register("io.dawn.macrobenchmark") {
            id = "io.dawn.MacroBenchMark"
            implementationClass = "io.dawn.gradle.plugin.module.MacroBenchMarkConvention"
        }
*/
        register("io.dawn.compose") {
            id = "io.dawn.compose"
            implementationClass = "io.dawn.gradle.plugin.feature.ComposeConvention"
        }

        register("io.dawn.hilt") {
            id = "io.dawn.hilt"
            implementationClass = "io.dawn.gradle.plugin.feature.HiltConvention"
        }

        register("io.dawn.test") {
            id = "io.dawn.test"
            implementationClass = "io.dawn.gradle.plugin.feature.TestConvention"
        }
    }
}