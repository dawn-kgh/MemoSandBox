package io.dawn.gradle.plugin

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

internal val Project.libs: VersionCatalog
    get() = this.extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.test: VersionCatalog
    get() = this.extensions.getByType<VersionCatalogsExtension>().named("test")

internal fun DependencyHandlerDelegate.implementation(dependencyNotation: Any) =
    this.add("implementation", dependencyNotation)

internal fun DependencyHandlerDelegate.ksp(dependencyNotation: Any) =
    this.add("ksp", dependencyNotation)

internal fun DependencyHandlerDelegate.testImplementation(dependencyNotation: Any) =
    this.add("testImplementation", dependencyNotation)

internal fun DependencyHandlerDelegate.runtimeOnly(dependencyNotation: Any) =
    this.add("runtimeOnly", dependencyNotation)

internal fun DependencyHandlerDelegate.api(dependencyNotation: Any) = this.add("api", dependencyNotation)

context(DependencyHandlerDelegate)
internal fun VersionCatalog.api(libraryAlias: String): Unit = findLibrary(libraryAlias).ifPresent { provider ->
    add("api", provider)
}

context(DependencyHandlerDelegate)
internal fun VersionCatalog.implementation(libraryAlias: String): Unit =
    findLibrary(libraryAlias).ifPresent { provider ->
        add("implementation", provider)
    }

context(DependencyHandlerDelegate)
internal fun VersionCatalog.bundleImplementation(bundleName: String): Unit =
    findBundle(bundleName).ifPresent { provider ->
        add("implementation", provider)
    }

context(DependencyHandlerDelegate)
internal fun VersionCatalog.bundleTestImplementation(bundleName: String): Unit =
    findBundle(bundleName).ifPresent { provider ->
        add("testImplementation", provider)
    }

context(DependencyHandlerDelegate)
internal fun VersionCatalog.testImplementation(libraryAlias: String): Unit =
    findLibrary(libraryAlias).ifPresent { provider ->
        add("testImplementation", provider)
    }

context(DependencyHandlerDelegate)
internal fun VersionCatalog.androidTestImplementation(libraryAlias: String): Unit =
    findLibrary(libraryAlias).ifPresent { provider ->
        add("androidTestImplementation", provider)
    }

context(DependencyHandlerDelegate)
internal fun VersionCatalog.bundleAndroidTestImplementation(bundleName: String): Unit =
    findBundle(bundleName).ifPresent { provider ->
        add("androidTestImplementation", provider)
    }

context(DependencyHandlerDelegate)
internal fun VersionCatalog.ksp(libraryAlias: String): Unit = findLibrary(libraryAlias).ifPresent { provider ->
    add("ksp", provider)
}

internal fun Project.applyKsp(kspExtensionConfiguration: KspExtension.() -> Unit = {}) {
    with(pluginManager) {
        apply("com.google.devtools.ksp")
    }

    extensions.configure<KspExtension>(kspExtensionConfiguration)

    extensions.findByType(LibraryExtension::class.java)?.let {
        configure<LibraryExtension> {
            libraryVariants.all {
                sourceSets {
                    getByName(name) {
                        java.srcDirs("build/generated/ksp/$name/kotlin")
                    }
                }
            }
        }
    }

    extensions.findByType(AppExtension::class.java)?.let {
        configure<AppExtension> {
            applicationVariants.all {
                sourceSets {
                    getByName(name) {
                        java.srcDirs("build/generated/ksp/$name/kotlin")
                    }
                }
            }
        }
    }
}
