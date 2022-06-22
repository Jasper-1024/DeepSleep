package com.js.deepsleep.xposed

import com.js.deepsleep.BuildConfig
import com.js.deepsleep.xposed.hook.*
import com.js.deepsleep.xposed.model.XpNSP
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage


class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        XpUtil.log("initZygote")
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XpUtil.packageName = lpparam.packageName // packageName
        XpNSP.getInstance(lpparam.packageName) // init

        when (lpparam.packageName) {
            BuildConfig.APPLICATION_ID -> SelfXp.hook(lpparam) // self hook
            "com.android.providers.settings" -> SettingsProviderXP.hook(lpparam) //system settings providers hook
            else -> { //other
                try {
                    XpNSP.getInstance().apply {
                        getSt()
                        getExtends()
                    }
                    WakelockXp.hook(lpparam)
                    AlarmXp.hook(lpparam)
                    ServiceXp.hook(lpparam)
                    SyncXp.hook(lpparam)
                } catch (e: Throwable) {
                    XpUtil.log("get context err $e")
                }
            }
        }
    }
}