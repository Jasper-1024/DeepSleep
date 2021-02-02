package com.js.deepsleep

import android.app.Application
import android.content.Context

class BasicApp : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}