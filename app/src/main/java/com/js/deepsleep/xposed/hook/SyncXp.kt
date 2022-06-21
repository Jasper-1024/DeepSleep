package com.js.deepsleep.xposed.hook

import com.js.deepsleep.tools.Type
import com.js.deepsleep.xposed.XpNSP
import com.js.deepsleep.xposed.XpUtil
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage

class SyncXp {
    companion object {
        fun hook(lpparam: XC_LoadPackage.LoadPackageParam) {
            val tmp: Class<*>? =
                XpUtil.getClass("android.content.ContentResolver", lpparam.classLoader)

            tmp?.let {
                XposedBridge.hookAllMethods(it, "requestSync", HandleSync())
                XposedBridge.hookAllMethods(it, "addPeriodicSync", HandleSync())
                XposedBridge.hookAllMethods(it, "setSyncAutomatically", HandleSync())
            }
        }
    }

    class HandleSync : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
//            XpUtil.log("sync hook")
            if (XpNSP.getInstance().block(Type.Sync, "", "")) {
                param.result = null
                XpUtil.log("sync block")
            }

        }

//        @Throws(Throwable::class)
//        override fun afterHookedMethod(param: MethodHookParam) {
//            super.afterHookedMethod(param)
//
////            XpUtil.log("service hook2")
//
//        }
    }
}