# xumak test

Overview
The WTA Android Code Challenge consists of an Android application that displays the list of
Breaking Bad Characters using a publicly available REST API on the web. Clicking on a
character opens a simple detail screen with more information about the character.
-----------------------
It should be possible for the user to browse the list of characters. Users of the app should be
able to toggle a character as a favorite and these selections should be persisted across restarts
of the app. Favorite characters should appear at the top of the list when the list is loaded. The
details screen should show the character’s occupation and status obtained from the API
endpoint.
The code is written in Kotlin and Java Android. 
We would like to see a bug-free app, with a clean UI, that is written with
a modern architecture. The is compatible with Android 6.0 or above (API Level 23+).

Deliverables
Please submit your source code via a link to Github.
API Spec:
/api/characters?limit<LIMIT>&offset=<OFFSET>
Optional parameters:
● limit (integer)
● offset (integer)
Example: ​ https://www.breakingbadapi.com/api/characters?limit=100
You may find the following publicly available API at ​ https://www.breakingbadapi.com

## Screenshots 🚀

![Screen1](https://github.com/ezsocial/xumak/blob/main/Screenshot_20211017_222836.png)
![Screen3](https://github.com/ezsocial/xumak/blob/main/Screenshot_20211017_222853.png)

## Here insall the apk TEST in your smparthone 🚀
[![Download](https://github.com/ezsocial/xumak/blob/main/app-debug.apk)]
### Pre-requisitos 📋
  android {
    compileSdkVersion 30
    buildToolsVersion "30.0.3"

    defaultConfig {
        applicationId "net.ezmovil.xumak"
        minSdkVersion 23
        targetSdkVersion 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

