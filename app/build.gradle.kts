plugins {
    id("io.dawn.app")
}

android {
    namespace = "io.dawn.app"
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":presentation:main:api"))
    runtimeOnly(project(":presentation:main:impl"))
}
