package com.js.deepsleep.base

import android.annotation.SuppressLint
import android.content.Context
import com.js.deepsleep.BasicApp
import com.js.deepsleep.BuildConfig


class SPTools {
    companion object {
        const val SP_NAME = "DeepSleep"

        private const val default_str = ""
        private const val default_bool = true

        @SuppressLint("WorldReadableFiles")
        private var prefs = try {
            // MODE_WORLD_READABLE, New XSharedPreferences
            BasicApp.context.getSharedPreferences(SP_NAME, Context.MODE_WORLD_READABLE)
        } catch (e: SecurityException) {
            null
        }

        fun getString(key: String, defaultValue: String = default_str): String {

            return prefs?.getString(key, defaultValue) ?: defaultValue
        }

        fun setString(key: String, value: String) {
            with(prefs?.edit() ?: return) {
                putString(key, value)
//                apply()
                commit()
            }
        }

        fun getBoolean(key: String, defaultValue: Boolean = default_bool): Boolean {
            return prefs?.getBoolean(key, defaultValue) ?: defaultValue
        }

        fun setBoolean(key: String, value: Boolean) {
            with(prefs?.edit() ?: return) {
                putBoolean(key, value)
//                apply()
                commit()
            }
        }
    }
}