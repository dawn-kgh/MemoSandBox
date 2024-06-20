package io.dawn.gradle.plugin

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

context(Project)
internal fun BaseExtension.configVersionAndSdk() {
    compileSdkVersion(34)
    buildToolsVersion("34.0.0")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    defaultConfig {
        targetSdk = 34
        minSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            consumerProguardFiles(
                "proguard-rules.pro",
                "${rootProject.projectDir}/proguard/kotlin-serialization-proguard-rules.pro",
                "${rootProject.projectDir}/proguard/coroutines-proguard-rules.pro",
                "${rootProject.projectDir}/proguard/network-proguard-rules.pro",
                "${rootProject.projectDir}/proguard/android-proguard-rules.pro",
                "${rootProject.projectDir}/proguard/kakao-proguard-rules.pro"
            )
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }
}

context(Project)
internal fun CommonExtension<*, *, *, *, *, *>.configBuildFeatures() {
    with(buildFeatures) {
        aidl = false
        renderScript = false
        shaders = false
        resValues = false
    }
}