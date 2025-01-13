import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
    kotlin("plugin.serialization") version "2.0.0"
    id("com.android.library") version "8.5.2"
    id("org.jetbrains.kotlin.android") version "1.9.0"
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.zarinpal"
            artifactId = "zarinPal-library"
            // Whenever the version is updated, it should be updated in the following path:
            // java/com/example/zarinpal/data/remote/PaymentService.kt
            // header("User-Agent", "ZarinPalSdk/v.1.0.0 (android kotlin)")
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

android {
    namespace = "com.example.zarinpal"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    publishing {
        singleVariant("release")
    }
}

dependencies {
    // Ktor
    val ktor_version = "1.6.3"

    api("io.ktor:ktor-client-core:$ktor_version")
    api("io.ktor:ktor-client-android:$ktor_version")
    api("io.ktor:ktor-client-serialization:$ktor_version")
    api("io.ktor:ktor-client-logging:$ktor_version")

    api("ch.qos.logback:logback-classic:1.2.3")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    api("androidx.core:core-ktx:1.13.0")
}

tasks.register<Jar>("createJar") {
    archiveBaseName.set("zarinPal-library")
    archiveVersion.set("1.0.0")

    manifest {
        attributes["Implementation-Title"] = "zarinPal-library"
        attributes["Implementation-Version"] = "1.0.0"
    }
}