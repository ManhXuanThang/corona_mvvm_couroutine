package dev.best.covidkotlin

import android.app.Application
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant((Timber.DebugTree()))
            Stetho.initializeWithDefaults(this)
        }

        if (BuildConfig.FLAVOR != "dev") {
//            handleCoroutineException()
        }
    }
}