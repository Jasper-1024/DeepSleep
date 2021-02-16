package com.js.deepsleep.xposed.hook

import com.js.deepsleep.BuildConfig
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class SelfXp {
    companion object {
        fun hook(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod(
                "${BuildConfig.APPLICATION_ID}.ui.mainactivity.MainActivity", lpparam.classLoader,
                "isModuleActive", HandleSelf()
            )
        }
    }

    class HandleSelf : XC_MethodHook() {

        override fun afterHookedMethod(param: MethodHookParam) {
            param.result = true
        }
    }
}