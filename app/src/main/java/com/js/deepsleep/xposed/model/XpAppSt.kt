package com.js.deepsleep.xposed.model

import android.content.Context
import android.os.Bundle
import com.js.deepsleep.base.Type
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.provider.PParameters
import com.js.deepsleep.data.provider.ProviderMethod
import com.js.deepsleep.xposed.XpUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class XpAppSt(private val packageName: String) {

    @Volatile
    var appSt: AppSt = AppSt()

    companion object {
        @Volatile
        private var instance: XpAppSt? = null

        fun getInstance(packageName: String = "") = instance ?: synchronized(this) {
            XpAppSt(packageName).also {
                instance = it
            }
        }
    }

    fun getSt(context: Context) {
        GlobalScope.launch {
            try {
                val tmp = Bundle()
                tmp.putString(PParameters.packageName, packageName)
                val bundle = XpUtil.call(ProviderMethod.GetAppSt, tmp, context)//get st
                bundle?.let { bun ->
                    bun.getSerializable(PParameters.appSt)?.let {//get st
                        synchronized(this) {
                            appSt = it as AppSt
                        }
                    }
                }
            } catch (e: Exception) {
                XpUtil.log("getSt err: $e")
            }
        }
    }

    fun block(type: Type, mPackageName: String, mTag: String): Boolean {
        return when (type) {
            Type.Wakelock -> appSt.wakelock
            Type.Alarm -> appSt.alarm
            Type.Service -> appSt.service
            Type.Sync -> appSt.sync
        }
    }
}