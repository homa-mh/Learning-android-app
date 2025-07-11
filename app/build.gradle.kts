
plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.learndatastructure"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.learndatastructure"
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
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")

    // RecyclerView Dependency
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // for using gson files
    implementation ("com.google.code.gson:gson:2.8.8")

    // OkHttp for api requests
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")

    //for animation
    implementation ("androidx.transition:transition:1.4.1")
    implementation ("com.airbnb.android:lottie:6.0.0")

    // for google ads
    implementation ("com.google.android.gms:play-services-ads:22.6.0")
}
