plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm()

    sourceSets {
        jvmMain.dependencies {
            implementation(projects.shared)
            implementation(compose.desktop.currentOs)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.jetbrains.kmpapp.MainKt"
    }
}
