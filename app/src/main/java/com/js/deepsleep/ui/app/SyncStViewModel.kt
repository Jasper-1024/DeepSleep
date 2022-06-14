package com.js.deepsleep.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.base.LogUtil
import com.js.deepsleep.base.SPTools
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.repository.syncsp.SyncSpRepo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class SyncStViewModel : ViewModel(), KoinComponent {
    private val syncSPR: SyncSpRepo by inject(named("syncSpR"))

    fun syncSp() {
        viewModelScope.launch {
            syncSPR.getAllAppSt().collect() { appSts ->
                appSts.map { appSt ->
                    saveSt2SP(appSt)
                }
//                LogUtil.d("SyncStViewModel", "getSyncSp: ${appSts.size}")
            }
        }
    }

    private fun saveSt2SP(appSt: AppSt) {
        if (appSt.flag) { // if app has being limited
            SPTools.setBoolean("${appSt.packageName}_wakelock", appSt.wakelock)
            SPTools.setBoolean("${appSt.packageName}_alarm", appSt.alarm)
            SPTools.setBoolean("${appSt.packageName}_service", appSt.service)
            SPTools.setBoolean("${appSt.packageName}_sync", appSt.sync)
            SPTools.setBoolean("${appSt.packageName}_broadcast", appSt.broadcast)
        } else {
            if (!SPTools.getBoolean("${appSt.packageName}_wakelock")) {
                SPTools.setBoolean("${appSt.packageName}_wakelock", appSt.wakelock)
            }
            if (!SPTools.getBoolean("${appSt.packageName}_alarm")) {
                SPTools.setBoolean("${appSt.packageName}_alarm", appSt.alarm)
            }
            if (!SPTools.getBoolean("${appSt.packageName}_service")) {
                SPTools.setBoolean("${appSt.packageName}_service", appSt.service)
            }
            if (!SPTools.getBoolean("${appSt.packageName}_sync")) {
                SPTools.setBoolean("${appSt.packageName}_sync", appSt.sync)
            }
        }
    }
}