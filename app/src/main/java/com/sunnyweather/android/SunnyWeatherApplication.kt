package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "347e1eba80d3400d87fa14e5f3ed9d7e"
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}