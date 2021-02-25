package com.js.deepsleep.xposed.hook

import android.content.Context
import android.content.Intent
import com.js.deepsleep.xposed.XpUtil
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class BroadcastXP {
    companion object {
        fun hook(lpparam: XC_LoadPackage.LoadPackageParam) {

            XpUtil.run {
                XposedHelpers.findAndHookMethod(
                    "android.content.BroadcastReceiver",
                    lpparam.classLoader,
                    "onReceive",
                    Context::class.java,
                    Intent::class.java,
                    Test()
                )
            }

            val tmp: Class<*>? =
                XpUtil.getClass("android.content.BroadcastReceiver", lpparam.classLoader)

            tmp?.let {
                XposedBridge.hookAllMethods(it, "onReceive", Test())
            }
        }
    }

    class Test : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun beforeHookedMethod(param: MethodHookParam) {
            XpUtil.log("broadcast hook1")
            if (param.args[0] !is Context && param.args[0] != null) return
            if (param.args[1] !is Intent && param.args[1] != null) return
            val context = param.args[0] as Context
            val intent = param.args[1] as Intent
            XpUtil.log("broadcast ${context.packageName} ${intent.action}")
//            context.packageName
//            intent.action
//            if (XpAppSt.getInstance().block(Type.Broadcast, "", "")) {
//                param.result = null
//                XpUtil.log("service block ${it.className}")
//            }
        }

        @Throws(Throwable::class)
        override fun afterHookedMethod(param: MethodHookParam) {
            XpUtil.log("broadcast hook2")
            if (param.args[0] !is Context && param.args[0] != null) return
            if (param.args[1] !is Intent && param.args[1] != null) return
            val context = param.args[0] as Context
            val intent = param.args[1] as Intent
            XpUtil.log("broadcast ${context.packageName} ${intent.action}")
//            param.result = null
        }
    }
}