plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.jetbrains.kotlin.serialization)
  alias(libs.plugins.google.devtools.ksp)
}

kotlin {
  jvmToolchain(11)
}

android {
  namespace = "com.example.imageviewer"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.example.imageviewer"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    debug {
      buildConfigField("String", "IMAGE_VIEWER_BASE_URL", "\"https://mocki.io/v1/\"")
      buildConfigField(
        "String",
        "FORM_NEST_ENDPOING",
        "\"a399eb5c-25b7-4cea-bfa1-1d12cb1ba48f\""
      )
    }
    release {
      buildConfigField("String", "IMAGE_VIEWER_BASE_URL", "\"https://mocki.io/v1/\"")
      buildConfigField(
        "String",
        "FORM_NEST_ENDPOING",
        "\"a399eb5c-25b7-4cea-bfa1-1d12cb1ba48f\""
      )
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  // Networking dependencies
  implementation(libs.retrofit)
  implementation(libs.okhttp)
  implementation(libs.okhttp.logginginterceptor)
  implementation(libs.moshi)
  implementation(libs.moshi.kotlin)
  implementation(libs.converter.moshi)

  //ViewModel/Activity
  implementation(libs.androidx.activity.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  //Coil
  implementation(libs.coil.compose)
  implementation(libs.coil.okhttp)

  //Nav3
  implementation(libs.androidx.navigation3.ui)
  implementation(libs.androidx.navigation3.runtime)
  implementation(libs.androidx.lifecycle.viewmodel.navigation3)
  implementation(libs.kotlinx.serialization.core)

  //Testing
  testImplementation(libs.turbine)
  testImplementation(libs.kotlinx.coroutines.test)
}