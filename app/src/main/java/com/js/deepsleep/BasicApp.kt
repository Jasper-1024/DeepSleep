package com.js.deepsleep

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BasicApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var gson: Gson
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        gson = Gson()

        //koin
        startKoin {
            androidContext(this@BasicApp)
            modules(repository, viewModel)
        }
    }
}