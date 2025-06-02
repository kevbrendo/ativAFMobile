plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "br.com.agoracomecouaviagem.controleioverland"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.agoracomecouaviagem.controleioverland"
        minSdk = 16
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.itextpdf:itext7-core:7.1.15")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.nineoldandroids:library:2.4.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")


}


