package com.xplor.android.challenge

import android.app.Application
import com.xplor.android.challenge.di.AppComponent
import com.xplor.android.challenge.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.*

open class App: Application() {

    val appComponent by lazy {
        initializeAppComponent()
    }

    open fun initializeAppComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}
