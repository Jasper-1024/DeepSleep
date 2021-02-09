package com.js.deepsleep.ui.app

import androidx.annotation.LayoutRes
import androidx.databinding.ObservableBoolean
import com.js.deepsleep.data.db.entity.App
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.ui.databinding.item.BaseItem
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class ItemApp(
    override var data: App,
    override val handle: HandleApp,
    @LayoutRes override val layoutId: Int
) : BaseItem(data, handle, layoutId) {

    // 点击状态
    var load = ObservableBoolean().apply { this.set(false) }

    override fun getID(): String {
        return data.info.packageName
    }

    override fun getContent(): Int {
        return when (data.st) {
            null -> 0
            else -> getStContent(data.st!!)
        }
    }

    // 获取 appSt 的不同
    private fun getStContent(appSt: AppSt): Int {
        return getFlag(appSt.alarm) + getFlag(appSt.wakelock) * 2 + getFlag(appSt.server) * 4
    }

    private fun getFlag(boolean: Boolean): Int {
        return when (boolean) {
            true -> 1
            false -> 0
        }
    }
}