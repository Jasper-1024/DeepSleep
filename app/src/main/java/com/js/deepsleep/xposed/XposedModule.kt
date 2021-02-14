package com.js.deepsleep.xposed

import com.js.deepsleep.xposed.hook.Wakelock
import com.js.deepsleep.xposed.hook.XpContext
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

        // 获取 AppSt 设置
        try {
            XpContext.hook { XpAppSt.getInstance().getSt(it) }
        } catch (e: Throwable) {
            XpUtil.log("get context err $e")
        }

        Wakelock.hook(lpparam)
    }
}