package com.js.deepsleep.ui.app

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.BasicApp
import com.js.deepsleep.R
import com.js.deepsleep.data.db.entity.App
import com.js.deepsleep.data.db.entity.AppInfo
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.repository.app.AppRepo
import com.js.deepsleep.data.repository.extend.ExtendRepo
import com.js.deepsleep.tools.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.text.Collator
import java.util.*
import kotlin.collections.set


class AppViewModel : ViewModel(), KoinComponent {


    private val appAR: AppRepo by inject(named("AppR"))

    private val extendR: ExtendRepo by inject(named("wakelockER"))

    private val handleApp = HandleApp(this)

    // 更新应用列表
    init {
        syncApp()
    }

    // 更新应用列表
    fun syncApp() {
        viewModelScope.launch(Dispatchers.Default) {
            appAR.syncAppList()
        }
    }

    // 保存 appSt
    fun saveSt(appSt: AppSt) {
        viewModelScope.launch(Dispatchers.Default) {
            appSt.let {
                it.flag = it.wakelock || it.alarm || it.service || it.sync || it.broadcast
            }
            appAR.setAppSt(appSt)
        }
    }

    // call system force stop app
    fun stopApp(appInfo: AppInfo) {

        if (appInfo.persistent || appInfo.system) {// if app is system or persistent
            return
        }
        viewModelScope.launch(Dispatchers.Default) {
            val result = callStopApp(appInfo.packageName, appInfo.uid)
            withContext(Dispatchers.Main) {
                isSuccess(result)
            }
        }
    }

    private fun isSuccess(result: Boolean) {
        if (!result) {
            Toast.makeText(
                BasicApp.context,
                BasicApp.context.getString(R.string.forcestopEnable),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    val apps = appAR.getApps().asLiveData()

    // recyclerview 的 list
    var list = MediatorLiveData<List<ItemApp>>()

    fun getList(apps: List<App>, type: AppType, query: String, sort: Sort): List<ItemApp> {
        return apps.appType(appType(type))
            .search(query, ::search)
            .sort(sort(sort))
            .toItemApps()
    }

    // load 状态
    private val map = mutableMapOf<String, Boolean>()
    private fun getLoad(packageName: String): Boolean = map.getOrPut(packageName) { false }
    fun setLoad(itemApp: ItemApp) {
        itemApp.load.apply { this.set(!this.get()) }
        map[itemApp.data.info.packageName] = itemApp.load.get()
    }


    private fun List<App>.toItemApps(): List<ItemApp> {
        return this.map { app ->
            ItemApp(
                app,
                handleApp,
                R.layout.item_app
            ).apply { this.load.set(getLoad(this.data.info.packageName)) }
        }
    }

    //筛选 App 类型
    private fun appType(a: AppType): (App) -> Boolean {
        return when (a) {
            AppType.User -> ::useApp
            AppType.System -> ::systemApp
            AppType.Restricted -> ::restricted
            AppType.All -> ::allApp
            AppType.Extend -> ::extendApp
        }
    }

    private fun useApp(app: App) = !app.info.system
    private fun systemApp(app: App) = app.info.system
    private fun restricted(app: App) = app.st!!.flag
    private fun allApp(app: App) = true


    private fun extendApp(app: App): Boolean {
        val wakelockEX = extendR.getExtend(app.info.packageName, Type.Wakelock)
        val alarmEX = extendR.getExtend(app.info.packageName, Type.Alarm)
        if (wakelockEX != null && alarmEX != null) {
            if (wakelockEX.allowList.isNotEmpty() || alarmEX.allowList.isNotEmpty()
                || wakelockEX.blockList.isNotEmpty() || alarmEX.blockList.isNotEmpty()
                || wakelockEX.rE.isNotEmpty() || alarmEX.rE.isNotEmpty()
            ) {
                return true
            }
        }
        return false
    }

    // 搜索
    private fun search(app: App) = "${app.info.label}${app.info.packageName}"


    // 排序
    private fun sort(sort: Sort): Comparator<App> {
        return when (sort) {
            Sort.Name -> sortByName()
            else -> sortByName()
        }
    }

    private fun sortByName(): Comparator<App> {
        return Comparator { s1, s2 ->
            Collator.getInstance(Locale.getDefault()).compare(s1.info.label, s2.info.label)
        }
    }

}
