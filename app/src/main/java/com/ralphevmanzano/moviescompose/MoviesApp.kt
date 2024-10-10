package com.ralphevmanzano.moviescompose

import android.app.Application
import com.shakebugs.shake.Shake
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Shake.getReportConfiguration().isConsoleLogsEnabled = false
        Shake.start(this, BuildConfig.SHAKE_API_KEY)
    }
}