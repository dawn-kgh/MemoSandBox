plugins {
    id("io.dawn.app")
}

dependencies {
    implementation(project(":core:design"))
    implementation(project(":presentation:main:api"))
    runtimeOnly(project(":presentation:main:impl"))
}
