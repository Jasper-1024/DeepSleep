package com.js.deepsleep.xposed.hook

import android.app.Application
import android.content.Context
import com.js.deepsleep.xposed.XpUtil
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

class XpContext {
    companion object {
        fun hook(method: (context: Context) -> Unit) {
            try {
                XposedHelpers.findAndHookMethod(
                    Application::class.java,
                    "attach",
                    Context::class.java,
                    object : XC_MethodHook() {
                        @Throws(Throwable::class)
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            val context = param.args[0] as Context
                            method(context)
                        }
                    })

            } catch (e: Throwable) {
                XpUtil.log("get context err $e")
            }
        }
    }
}