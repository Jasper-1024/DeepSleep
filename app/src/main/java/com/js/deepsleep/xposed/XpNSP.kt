package com.js.deepsleep.xposed

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.js.deepsleep.BuildConfig
import com.js.deepsleep.tools.SPTools
import com.js.deepsleep.tools.Type
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend
import de.robv.android.xposed.XSharedPreferences

class XpNSP(private val packageName: String) {

    @Volatile
    var appSt: AppSt = AppSt()

    @Volatile
    var wakelockEx: Extend = Extend()

    @Volatile
    var alarmEx: Extend = Extend()

    @Volatile
    private var pref: SharedPreferences? = null

    private var gson: Gson = Gson()

    companion object {

        @Volatile
        private var instance: XpNSP? = null

        fun getInstance(packageName: String = "") = instance ?: synchronized(this) {
            XpNSP(packageName).also {
                it.getPref()
                instance = it
            }
        }
    }

    fun getSt() {
        try {
            appSt.wakelock = getWakelock()
            appSt.alarm = getAlarm()
            appSt.sync = getSync()
            appSt.service = getService()
            appSt.broadcast = getBroadcast()

//            XpUtil.log("getSt $appSt")

        } catch (e: Exception) {
            XpUtil.log("getSt err: $e")
        }
    }

    fun getExtends() {
        try {
            wakelockEx.let {
                it.allowList = getAppSet(Type.Wakelock, "allowList")
                it.blockList = getAppSet(Type.Wakelock, "blockList")
                it.rE = getAppSet(Type.Wakelock, "rE")

//                XpUtil.log("getExtends wakelockEx $wakelockEx")
            }
            alarmEx.let {
                it.allowList = getAppSet(Type.Alarm, "allowList")
                it.blockList = getAppSet(Type.Alarm, "blockList")
                it.rE = getAppSet(Type.Alarm, "rE")

//                XpUtil.log("getExtends alarmEx $alarmEx")
            }
        } catch (e: Exception) {
            XpUtil.log("getExtends err: $e")
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

    private fun getPref() = pref ?: synchronized(this) {
        val p = XSharedPreferences(BuildConfig.APPLICATION_ID, SPTools.SP_NAME)
        pref = if (p.file.canRead()) p else null
    }

    private fun getWakelock() = run { getAppFlag(Type.Wakelock) }
    private fun getAlarm() = run { getAppFlag(Type.Alarm) }
    private fun getService() = run { getAppFlag(Type.Service) }
    private fun getSync() = run { getAppFlag(Type.Sync) }
    private fun getBroadcast() = run { getAppFlag(Type.Broadcast) }

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

    private fun getAppSet(type: Type, index: String): Set<String> {
        return getSet("${packageName}_${type}_$index")
    }

    private fun getAppFlag(type: Type): Boolean {
        return getBool("${packageName}_${type}")
    }

    private fun getBool(key: String, defValue: Boolean = false): Boolean {
        return pref?.getBoolean(key, defValue) ?: defValue
    }

    private fun getSet(key: String): Set<String> {
        var str: String? = pref?.getString(key, "")
        return if (str.isNullOrBlank()) {
            mutableSetOf()
        } else {
            val type = object : TypeToken<Set<String>>() {}.type
            gson.fromJson(str, type)
        }
    }
}