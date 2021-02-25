package com.js.deepsleep.xposed.model

import android.content.Context
import android.os.Bundle
import com.js.deepsleep.base.Type
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend
import com.js.deepsleep.data.provider.PParameters
import com.js.deepsleep.data.provider.ProviderMethod
import com.js.deepsleep.xposed.XpUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class XpAppSt(private val packageName: String) {

    @Volatile
    var appSt: AppSt = AppSt()

    @Volatile
    var wakelockEx: Extend = Extend()

    @Volatile
    var alarmEx: Extend = Extend()

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

    fun getExtends(context: Context) {
        GlobalScope.launch {
            try {
                val tmp = Bundle()
                tmp.putString(PParameters.packageName, packageName)
                val bundle = XpUtil.call(ProviderMethod.GetExtends, tmp, context)//get st
                bundle?.let { bun ->
                    bun.getSerializable(PParameters.wakelock)?.let {
                        synchronized(this) {
                            wakelockEx = it as Extend
                        }
                    }
                    bun.getSerializable(PParameters.alarm)?.let {
                        synchronized(this) {
                            alarmEx = it as Extend
                        }
                    }
                }
            } catch (e: Exception) {
                XpUtil.log("getExtends err: $e")
            }
        }
    }

    fun block(type: Type, mPackageName: String, mTag: String): Boolean {
        return when (type) {
            Type.Wakelock -> getBlock(mTag, wakelockEx, appSt.wakelock)
            Type.Alarm -> getBlock(mTag, alarmEx, appSt.alarm)
            Type.Service -> appSt.service
            Type.Sync -> appSt.sync
            Type.Broadcast -> appSt.broadcast
        }
    }

    // 获取 Block
    private fun getBlock(mTag: String, extend: Extend, flag: Boolean): Boolean {
        if (mTag in extend.allowList) return false
        if (mTag in extend.blockList) return true
        if (getRE(mTag, extend.rE)) return true
        return flag
    }

    private fun getRE(mTag: String, rE: Set<String>): Boolean {
        if (rE.isEmpty()) {
            return false
        } else {
            rE.forEach {
                if (mTag.matches(Regex(it))) {
                    return true
                }
            }
            return false
        }
    }
}