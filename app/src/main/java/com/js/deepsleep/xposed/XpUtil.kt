package com.js.deepsleep.xposed

import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.js.deepsleep.data.provider.ProviderMethod
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers


object XpUtil {
    private const val Tag = "xposed.deepsleep"
    private const val authority = "com.js.deepsleep"// cp 地址

    var packageName: String = ""

    private const val log = true

    fun log(string: String) {
        if (log) {
            XposedBridge.log("$Tag $packageName: $string")
        }
    }

    fun call(method: ProviderMethod, bundle: Bundle, context: Context): Bundle? {
        val url = Uri.parse("content://${authority}")
        val contentResolver = context.contentResolver
        return try {
            contentResolver.call(url, method.value, null, bundle)
        } catch (e: Exception) {
            log(": record $method err: $e")
            null
        }
    }

    fun getClass(name: String, classLoader: ClassLoader): Class<*>? {
        return try {
            XposedHelpers.findClass(name, classLoader)
        } catch (e: Throwable) {
            XpUtil.log("alarm getClass err: $e")
            null
        }
    }

    fun run(name: String, function: () -> Unit) {
        try {
            function()
        } catch (e: Throwable) {
            log("$name err: $e")
        }
    }
}