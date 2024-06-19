plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-android")
    kotlin("kapt")

}

android {
    namespace = "com.example.myapplication"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    /*viewBinding {
        enabled = true
    }*/

   testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    /*tasks.withType<Test> {
        useJUnitPlatform()
    }*/

}
dependencies {
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.test:core-ktx:1.5.0")

    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    //JUnit4
    testImplementation("junit:junit:4.13.2")
    testImplementation ("com.google.truth:truth:1.1")
    testImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    //jupiter
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    /*ovaj ispod je menjan
    //androidTestImplementation("androidx.test.ext:junit-jupiter:1.1.5")
    androidTestImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")*/

    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.1")
    testImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")


    implementation("androidx.databinding:databinding-runtime:7.0.0")
    implementation("androidx.cardview:cardview:1.0.0")

    //mockito
    testImplementation ("org.mockito:mockito-junit-jupiter:3.12.4")
    testImplementation ("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito:mockito-android:4.0.0")
    testImplementation ("org.mockito:mockito-inline:4.0.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    implementation(kotlin("test"))

    implementation ("de.hdodenhof:circleimageview:3.1.0")



}

