plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.esieaboard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.esieaboard"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Fragment
    implementation ("androidx.fragment:fragment:1.6.2")

    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel:2.6.2")

    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata:2.6.2")

    // Room components
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")

    // Optional - Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.6.1")

    // Optional - Reactive Streams support for LiveData
    implementation ("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.6.2")
}