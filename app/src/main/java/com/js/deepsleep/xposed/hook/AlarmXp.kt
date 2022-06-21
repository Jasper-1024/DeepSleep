package com.js.deepsleep.xposed.hook

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Handler
import android.os.WorkSource
import com.js.deepsleep.tools.Type
import com.js.deepsleep.xposed.XpNSP
import com.js.deepsleep.xposed.XpUtil
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class AlarmXp {
    companion object {

        private var pendingIntent: Class<*>? = null

        fun hook(lpparam: XC_LoadPackage.LoadPackageParam) {

            pendingIntent = XpUtil.getClass("android.app.PendingIntent", lpparam.classLoader)

            pendingIntent?.let {
                XposedBridge.hookAllMethods(it, "getBroadcast", HandlePendingIntent(it))
                XposedBridge.hookAllMethods(it, "getActivity", HandlePendingIntent(it))
                XposedBridge.hookAllMethods(it, "getService", HandlePendingIntent(it))
            }

            XposedHelpers.findAndHookMethod(
                "android.app.AlarmManager",
                lpparam.classLoader,
                "setImpl",
                Int::class.java,
                Long::class.java,
                Long::class.java,
                Long::class.java,
                Int::class.java,
                PendingIntent::class.java,
                AlarmManager.OnAlarmListener::class.java,
                String::class.java,
                Handler::class.java,
                WorkSource::class.java,
                AlarmManager.AlarmClockInfo::class.java,
                HandleAlarm()
            )
        }

    }

    class HandlePendingIntent(private val pendingIntent: Class<*>) : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
//            XpUtil.log("alarm pendingIntent hook")

            if (param.args.size < 2) return
            if (param.args[2] == null) return
            if (param.args[2] !is Intent) return

            XpUtil.run("pendingIntent") {
                val alarmName = (param.args[2] as Intent).action ?: "null"
                XposedHelpers.setAdditionalStaticField(pendingIntent, "alarmName", alarmName)
            }
        }
    }


    class HandleAlarm : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
            val xpNSP = XpNSP.getInstance()

            val alarmName: String = param.args[7] as String?
                ?: (XposedHelpers.getAdditionalStaticField(
                    pendingIntent,
                    "alarmName"
                ) as String?).apply {
                    if (this != null) {
                        XposedHelpers.setAdditionalStaticField(pendingIntent, "alarmName", null)
                    }
                }
                ?: "null"

            if (major(alarmName) || xpNSP.block(Type.Alarm, XpUtil.packageName, alarmName)) {
                param.result = null
                XpUtil.log("alarm block $alarmName")
            }
        }

        private fun major(alarmName: String): Boolean {
            return "android" == XpUtil.packageName ||
                    alarmName == "android"
        }
    }
}
