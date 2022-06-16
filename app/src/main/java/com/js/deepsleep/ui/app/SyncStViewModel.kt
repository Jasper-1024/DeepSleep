package com.js.deepsleep.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.base.LogUtil
import com.js.deepsleep.base.SPTools
import com.js.deepsleep.base.Type
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
//        LogUtil.d("SyncSt", "getSyncSp: ${appSt}")

        if (appSt.flag) { // if app has being limited
            SPTools.setBoolean("${appSt.packageName}_${Type.Wakelock}", appSt.wakelock)
            SPTools.setBoolean("${appSt.packageName}_${Type.Alarm}", appSt.alarm)
            SPTools.setBoolean("${appSt.packageName}_${Type.Service}", appSt.service)
            SPTools.setBoolean("${appSt.packageName}_${Type.Sync}", appSt.sync)
            SPTools.setBoolean("${appSt.packageName}_${Type.Broadcast}", appSt.broadcast)
        } else {
            if (SPTools.getBoolean("${appSt.packageName}_${Type.Wakelock}")) {
                SPTools.setBoolean("${appSt.packageName}_${Type.Wakelock}", appSt.wakelock)
            }
            if (SPTools.getBoolean("${appSt.packageName}_${Type.Alarm}")) {
                SPTools.setBoolean("${appSt.packageName}_${Type.Alarm}", appSt.alarm)
            }
            if (SPTools.getBoolean("${appSt.packageName}_${Type.Service}")) {
                SPTools.setBoolean("${appSt.packageName}_${Type.Service}", appSt.service)
            }
            if (SPTools.getBoolean("${appSt.packageName}_${Type.Sync}")) {
                SPTools.setBoolean("${appSt.packageName}_${Type.Sync}", appSt.sync)
            }
            if (SPTools.getBoolean("${appSt.packageName}_${Type.Broadcast}")) {
                SPTools.setBoolean("${appSt.packageName}_${Type.Broadcast}", appSt.broadcast)
            }
        }
    }

//    private fun setFlag(type: Type) {
//        SPTools.setBoolean("${appSt.packageName}_${Type.Wakelock}", appSt.wakelock)
//    }
}