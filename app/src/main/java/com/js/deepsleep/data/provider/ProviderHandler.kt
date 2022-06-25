package com.js.deepsleep.data.provider

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.js.deepsleep.tools.LogUtil
import com.js.deepsleep.tools.Type
import com.js.deepsleep.data.db.AppDatabase
import com.js.deepsleep.data.db.entity.Extend
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
            ProviderMethod.GetExtends.value -> getExtends(bundle)
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

    // 获取 appst
    private fun getAppSt(bundle: Bundle): Bundle {
        val packageName: String = getPackageName(bundle)
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

    private fun getExtends(bundle: Bundle): Bundle {
        val packageName: String = getPackageName(bundle)

        val wakelockEx = runBlocking {
            getExtend(packageName, Type.Wakelock)
        }

        val alarmEx = runBlocking {
            getExtend(packageName, Type.Alarm)
        }

        return Bundle().apply {
            this.putSerializable(PParameters.wakelock, wakelockEx)
            this.putSerializable(PParameters.alarm, alarmEx)
        }
    }

    private fun getPackageName(bundle: Bundle): String {
        return bundle.getString(PParameters.packageName, "")
    }

    private suspend fun getExtend(packageName: String, type: Type): Extend? {
        return try {
            db.extendDao().getExtend(packageName, type.value)
        } catch (e: Exception) {
            null
        }
    }

}