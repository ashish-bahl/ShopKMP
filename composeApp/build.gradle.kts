import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.buildkonfig)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.projectDir.resolve("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val mockApiProjectSecret: String = localProperties.getProperty("mockApiProjectSecret") ?: ""

buildkonfig {
    packageName = "org.example.project"
    objectName = "ShopKMPConfig"
    
    defaultConfigs {
        // Reads from System Env first, then local properties, then empty string
        val secret = System.getenv("MOCK_API_SECRET")
            ?: mockApiProjectSecret
        buildConfigField(STRING, "MOCK_API_SECRET", secret)
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.client.okhttp)
            implementation(libs.coil.network.okhttp)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.bundles.ktor)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.navigation.compose)

            implementation(libs.material.icons.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)
            implementation(libs.material3.window.size.class1)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    debugImplementation(libs.compose.uiTooling)
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

ksp {
    arg("room.generateKotlin", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}
