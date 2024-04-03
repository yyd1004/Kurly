package com.example.kurly

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KurlyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}