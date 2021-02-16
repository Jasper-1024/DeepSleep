package com.js.deepsleep.xposed.hook

import android.content.ComponentName
import com.js.deepsleep.base.Type
import com.js.deepsleep.xposed.XpUtil
import com.js.deepsleep.xposed.model.XpAppSt
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

            val tmp: Class<*>? = XpUtil.getClass("android.app.ContextImpl", lpparam.classLoader)
            tmp?.let {
                XposedBridge.hookAllMethods(it, "startServiceCommon", HandleService())
//                XposedBridge.hookAllMethods(it, " bindServiceCommon", Test2())
            }

        }
    }

    class HandleService : XC_MethodHook() {
//        @Throws(Throwable::class)
//        override fun beforeHookedMethod(param: MethodHookParam) {
//            XpUtil.log("service hook")
//        }

        @Throws(Throwable::class)
        override fun afterHookedMethod(param: MethodHookParam) {
            super.afterHookedMethod(param)

//            XpUtil.log("service hook2")

            if (param.result == null) return
            if (param.result !is ComponentName) return

            val componentName = param.result as ComponentName?
            componentName?.let {
                if (XpAppSt.getInstance().block(Type.Service, it.packageName, it.className)) {
                    param.result = null
                    XpUtil.log("service block ${it.className}")
                }
            }
        }
    }

//    class Test2 : XC_MethodHook() {
//        @Throws(Throwable::class)
//        override fun beforeHookedMethod(param: MethodHookParam) {
//            XpUtil.log("service hook3")
//            if (param.args.size < 4) return
//            if (param.args[3] == null) return
//            if (param.args[3] !is String) return
//
//            val tmp: String = param.args[3] as String
//
//            XpUtil.log("service hook3: $tmp")
//        }
//
//        @Throws(Throwable::class)
//        override fun afterHookedMethod(param: MethodHookParam) {
//            super.afterHookedMethod(param)
//
//            XpUtil.log("service hook4")
//        }
//    }


//    class HandleService : XC_MethodHook() {
//        @Throws(Throwable::class)
//        override fun beforeHookedMethod(param: MethodHookParam) {
//            XpUtil.log("service hook")
//
////            if (param.args[0] == null) return
////            if (param.args[0] !is Intent) return
////
////            XpUtil.run("service hook") {
////                val serviceName = (param.args[0] as Intent).action ?: "null"
////                XpUtil.log("service $serviceName")
////            }
//        }
//    }

}