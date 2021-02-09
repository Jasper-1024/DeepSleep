package com.js.deepsleep.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.R
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.repository.app.AppRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

@KoinApiExtension
class AppViewModel : ViewModel(), KoinComponent {
    private val appAR: AppRepo by inject(named("AppR"))
    private val handleApp = HandleApp(this)

    // load 状态
    private val map = mutableMapOf<String, Boolean>()
    private fun getLoad(packageName: String): Boolean = map.getOrPut(packageName) { false }

    fun setLoad(itemApp: ItemApp) {
        itemApp.load.apply { this.set(!this.get()) }
        map[itemApp.data.info.packageName] = itemApp.load.get()
    }


    // appList App 类型 -> ItemApp
    var list = appAR.getApps().map { lists ->
        lists.map { app ->
            ItemApp(app, handleApp, R.layout.item_app).apply {
                this.load.set(getLoad(this.data.info.packageName))
            }
        }
    }.asLiveData()


    // 更新应用列表
    init {
        syncApp()
    }

    // 保存 Appst
    fun saveSt(appSt: AppSt) {
        viewModelScope.launch(Dispatchers.Default) {
            appAR.setAppSt(appSt)
        }
    }

    // 更新应用列表
    fun syncApp() {
        viewModelScope.launch(Dispatchers.Default) {
            appAR.syncAppList()
        }
    }

}
