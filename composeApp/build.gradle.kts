import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dokka)
}

kotlin {
    android {
        namespace = "com.jetbrains.kmpapp"
        compileSdk = 36
        minSdk = 24

        withJava()
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
        commonMain.dependencies {
            implementation(libs.bundles.compose)

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

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}
