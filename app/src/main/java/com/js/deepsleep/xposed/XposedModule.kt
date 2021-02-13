package com.js.deepsleep.xposed

import android.content.Context
import com.js.deepsleep.xposed.model.XpAppSt
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.XposedHelpers.findClass
import de.robv.android.xposed.callbacks.XC_LoadPackage


class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        XpUtil.log("initZygote")
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XpUtil.packageName = lpparam.packageName

        val xpAppSt = XpAppSt.getInstance(lpparam.packageName)

        try {
            val contextClass = findClass("android.content.ContextWrapper", lpparam.classLoader)
            findAndHookMethod(contextClass, "getApplicationContext", object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    val context = param.result as Context
                    xpAppSt.getSt(context)

                    XpUtil.log("${lpparam.packageName} : ${xpAppSt.appSt}")
                }
            })
        } catch (e: Throwable) {
            XpUtil.log("get context err $e")
        }

    }
}