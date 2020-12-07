plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
//    id("com.google.firebase.crashlytics")
//    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
//    id("com.google.firebase.firebase-perf")
    jacoco
}

android {

    defaultConfig {
        applicationId = "dev.best.covidkotlin"
        buildToolsVersion("30.0.2")
        minSdkVersion(17)
        compileSdkVersion(30)
        targetSdkVersion(30)
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
//            firebaseCrashlytics {
//                mappingFileUploadEnabled = false
//            }
        }
        create("staging") {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
//            firebaseCrashlytics {
//                mappingFileUploadEnabled = true
//            }
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
//            firebaseCrashlytics {
//                mappingFileUploadEnabled = true
//            }
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions("default")

    productFlavors {
        create("dev") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Covid2019")
            buildConfigField("boolean", "MOCK_DATA", "false")
        }
        create("mock") {
            versionCode = 1
            versionName = "1.0.0"
            applicationIdSuffix = ".mock"
            resValue("string", "app_name", "Covid2019 Mock")
            buildConfigField("boolean", "MOCK_DATA", "true")
        }
        create("prd") {
            versionCode = 1
            versionName = "1.0.0"
            resValue("string", "app_name", "Covid2019")
            buildConfigField("boolean", "MOCK_DATA", "false")
        }
    }

    applicationVariants.all {
        buildConfigField("String", "BASE_URL", "\"https://api.covid19api.com/\"")
        when (name) {
            "dev" -> {
            }
            "prd" -> {
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

    // https://developer.android.com/topic/libraries/data-binding
    buildFeatures {
        dataBinding = true
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    // common
    implementation(Libs.appcompat)
    implementation(Libs.legacySupport)
    implementation(Libs.constraintLayout)
    implementation(Libs.recyclerview)
    implementation(Libs.material)
    implementation(Libs.stdLib)
    implementation(Libs.multidex)

    // List of KTX extensions
    // https://developer.android.com/kotlin/ktx/extensions-list
    implementation(Libs.coreKtx)
//    implementation(Libs.activityKtx)
    implementation(Libs.fragmentKtx)

    // Lifecycle
    // https://developer.android.com/jetpack/androidx/releases/lifecycle
    implementation(Libs.lifecycleViewModelKtx)
    implementation(Libs.lifecycleLiveDataKtx)
    implementation(Libs.lifecycleKtx)
    implementation(Libs.lifecycleJava8)

    // room
    // https://developer.android.com/topic/libraries/architecture/room
    implementation(Libs.roomRuntime)
    kapt(Libs.roomCompiler)
    implementation(Libs.roomKtx)

    // paging
    // https://developer.android.com/topic/libraries/architecture/paging
    implementation(Libs.paging)

    // navigation
    // https://developer.android.com/jetpack/androidx/releases/navigation
    implementation(Libs.navigationRuntimeKtx)
    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUiKtx)
//    implementation(Libs.navigationDynamicModule)

    // work
    // https://developer.android.com/topic/libraries/architecture/workmanager
//    implementation(Libs.workManager)

    // rx
    // https://github.com/ReactiveX/RxJava
//    implementation(Libs.rxjava)

    // coroutines
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesAndroid)
    testImplementation(Libs.coroutinesTest)

    // gson
    implementation(Libs.gson)

    // retrofit
    // https://github.com/square/retrofit
    implementation(Libs.retrofit)
    implementation(Libs.retrofitGson)
    implementation(Libs.okLogging)
//    implementation(Libs.retrofitRxjava)

    // stetho
    // http://facebook.github.io/stetho/
    implementation(Libs.stetho)
    implementation(Libs.stethoOkhttp3)

    // glide
    // https://github.com/bumptech/glide
    implementation(Libs.glideRuntime)
    kapt(Libs.glideCompiler)

    //dagger hilt
    implementation(Libs.daggerHiltAndroid)
    kapt(Libs.daggerHiltAndroidCompiler)
    implementation(Libs.daggerHiltViewModel)
    kapt(Libs.daggerHiltViewModelCompiler)

    // runtime permission
    // https://github.com/googlesamples/easypermissions
//    implementation(Libs.easyPermissions)

    // firebase
    // https://firebase.google.com/docs/android/setup
//    implementation(Libs.firebaseAnalytics)
//    implementation(Libs.firebaseCrashlytics)
//    implementation(Libs.firebasePerformance)

    // lottie
    // https://github.com/airbnb/lottie-android
//    implementation(Libs.lottie)

    // timber
    // https://github.com/JakeWharton/timber
    implementation(Libs.timber)

    implementation(Libs.viewpager2)

    compileOnly(Libs.lombok)
    kapt(Libs.annotationLombok)

    //loading
    implementation(Libs.loadingSpinKit)

    // unit test
    testImplementation(Libs.junit)
    testImplementation(Libs.mockitoCore)
    testImplementation(Libs.archCore)
}

jacoco {
    toolVersion = "0.8.6"
}
