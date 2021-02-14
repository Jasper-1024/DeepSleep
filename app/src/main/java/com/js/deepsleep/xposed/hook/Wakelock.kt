package com.js.deepsleep.xposed.hook

import android.os.PowerManager
import com.js.deepsleep.base.Type
import com.js.deepsleep.xposed.XpUtil
import com.js.deepsleep.xposed.model.XpAppSt
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Wakelock {
    companion object {
        fun hook(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod(
                "android.os.PowerManager.WakeLock",
                lpparam.classLoader,
                "acquire",
                HandleWakelock()
            )

            XposedHelpers.findAndHookMethod(
                "android.os.PowerManager.WakeLock",
                lpparam.classLoader,
                "acquire",
                Long::class.java,
                HandleWakelock()
            )
        }

    }

    class HandleWakelock : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {

            if (param.thisObject !is PowerManager.WakeLock) return

            val xpAppSt = XpAppSt.getInstance()

            val mPackageName = getString(param, "mPackageName")
            val mTag = getString(param, "mTag")


            //非关键应用               和 设置禁止
            if (!major(mPackageName) && xpAppSt.block(Type.Wakelock, mPackageName, mTag)) {
                param.result = null
                XpUtil.log("block $mTag")
            }

        }

        private fun getString(param: MethodHookParam, info: String): String {
            val tmp = param.thisObject::class.java.getDeclaredField(info)
                .apply { this.isAccessible = true }
            return (tmp[param.thisObject] as String).trim { it <= ' ' }
        }

        private fun major(mPackageName: String): Boolean {
            return "android" == XpUtil.packageName ||
                    mPackageName == "android" ||
                    mPackageName == "com.android.systemui" ||
                    mPackageName == "com.android.phone"
        }
    }
}