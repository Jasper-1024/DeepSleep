package com.js.deepsleep.data.provider

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.js.deepsleep.base.LogUtil
import com.js.deepsleep.data.db.AppDatabase
import kotlinx.coroutines.runBlocking

class ProviderHandler(
    context: Context
) {
    private val tag = "ProviderHandler"

    private var db: AppDatabase = AppDatabase.getInstance(context)

    companion object {
        @Volatile
        private var instance: ProviderHandler? = null

        fun getInstance(context: Context): ProviderHandler {
            if (instance == null) {
                instance = ProviderHandler(context)
            }
            return instance!!
        }
    }

    fun getMethod(methodName: String, bundle: Bundle): Bundle? {
        return when (methodName) {
            ProviderMethod.GetAppSt.value -> getAppSt(bundle)
            "test" -> test(bundle)
            else -> null
        }
    }

    @VisibleForTesting
    fun test(bundle: Bundle): Bundle {
        val test = bundle.get("Test") as String?

        LogUtil.d(tag, "$test")

        val tmp = Bundle()
        tmp.putString("Test", "Test")
        return tmp
    }

    private fun getAppSt(bundle: Bundle): Bundle {
        val packageName: String = bundle.getString(PParameters.packageName, "")
        val tmp = runBlocking {
            try {
                db.appStDao().AppSt(packageName)
            } catch (e: Exception) {
                null
            }
        }
        return Bundle().apply {
            this.putSerializable(PParameters.appSt, tmp)
        }
    }


}