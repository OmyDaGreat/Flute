plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "xyz.malefic.flute"
    compileSdk = 37

    defaultConfig {
        applicationId = "xyz.malefic.flute"
        minSdk = 24
        targetSdk = 37
        versionCode =
            providers
                .gradleProperty("appVersionCode")
                .map { it.toIntOrNull() ?: 1 }
                .orElse(1)
                .get()
        versionName = rootProject.version.toString()
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.activity.compose)
    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
}
