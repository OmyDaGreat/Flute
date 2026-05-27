plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
}

// Centralized app version, overridable from CI via -PappVersion.
version = providers.gradleProperty("appVersion").orElse("1.0.0-dev.local").get()

tasks.register("desktopAutoReload", Exec::class) {
    group = "run"
    description = "Runs the desktop application with auto-reload"
    executable = "./gradlew"
    args = listOf(":desktopApp:hotRunJvm", "--auto")
    workingDir = rootDir
}
