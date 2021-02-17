package com.js.deepsleep.xposed

import com.js.deepsleep.BuildConfig
import com.js.deepsleep.xposed.hook.*
import com.js.deepsleep.xposed.model.XpAppSt
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage


class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        XpUtil.log("initZygote")
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XpUtil.packageName = lpparam.packageName
        XpAppSt.getInstance(lpparam.packageName)

        // hook deepsleep
        if (lpparam.packageName == BuildConfig.APPLICATION_ID) {
            SelfXp.hook(lpparam)
        } else {
            try {
                XpContext.hook {
                    XpAppSt.getInstance().apply {
                        getSt(it) // 获取 AppSt
                        getExtends(it)// 获取 extend
                    }
                }
            } catch (e: Throwable) {
                XpUtil.log("get context err $e")
            }

            WakelockXp.hook(lpparam)
            AlarmXp.hook(lpparam)
            ServiceXp.hook(lpparam)
            SyncXp.hook(lpparam)
        }
    }
}