package com.js.deepsleep.xposed.hook

import android.content.ComponentName
import com.js.deepsleep.tools.Type
import com.js.deepsleep.xposed.XpNSP
import com.js.deepsleep.xposed.XpUtil
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage


class ServiceXp {
    companion object {
        fun hook(lpparam: XC_LoadPackage.LoadPackageParam) {
//            val contextWrapperClass =
//                XpUtil.getClass("android.content.ContextWrapper", lpparam.classLoader)
//            XposedHelpers.findAndHookMethod(
//                contextWrapperClass,
//                "startService",
//                Intent::class.java,
//                HandleService()
//            )
//            val tmp: Class<*>? = XpUtil.getClass("android.app.Service", lpparam.classLoader)
//            tmp?.let {
//                XposedBridge.hookAllMethods(it, "onCreate", Test())
//            }

            val tmp2: Class<*>? = XpUtil.getClass("android.app.ContextImpl", lpparam.classLoader)
            tmp2?.let {
                XposedBridge.hookAllMethods(it, "startServiceCommon", HandleService())
            }

        }
    }

    class HandleService : XC_MethodHook() {
        @Throws(Throwable::class)
        override fun afterHookedMethod(param: MethodHookParam) {
            super.afterHookedMethod(param)

            if (param.result == null) return
            if (param.result !is ComponentName) return

            val componentName = param.result as ComponentName?
            componentName?.let {
                if (XpNSP.getInstance().block(Type.Service, it.packageName, it.className)) {
                    param.result = null
                    XpUtil.log("service block ${it.className}")
                }
            }
        }
    }

//    class Test : XC_MethodHook() {
//        @Throws(Throwable::class)
//        override fun beforeHookedMethod(param: MethodHookParam) {
//            XpUtil.log("service hook3")
//            param.result = null
//        }
//
//        @Throws(Throwable::class)
//        override fun afterHookedMethod(param: MethodHookParam) {
//            XpUtil.log("service hook4")
//            param.result = null
//        }
//    }

}