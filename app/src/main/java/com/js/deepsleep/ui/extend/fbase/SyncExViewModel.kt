package com.js.deepsleep.ui.extend.fbase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.BasicApp
import com.js.deepsleep.tools.SPTools
import com.js.deepsleep.data.db.entity.Extend
import com.js.deepsleep.data.repository.syncsp.SyncSpRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class SyncExViewModel : ViewModel(), KoinComponent {
    private val syncSPR: SyncSpRepo by inject(named("syncSpR"))

    fun syncEx() {
        viewModelScope.launch(Dispatchers.IO) {
            syncSPR.getAllAppStEx().collect { exs ->
                exs.map {
                    saveEx2SP(it)
                }
//                LogUtil.d("syncEx", "${exs.size}")
            }
        }
    }

    private fun saveEx2SP(ex: Extend) {

//        LogUtil.d("SyncSt", "getSyncSp: ${ex}")

//        if (ex.allowList.isNotEmpty()) {
        SPTools.setString(
            "${ex.packageName}_${ex.type}_allowList",
            BasicApp.gson.toJson(ex.allowList)
        )
//        }

//        if (ex.blockList.isNotEmpty()) {
        SPTools.setString(
            "${ex.packageName}_${ex.type}_blockList",
            BasicApp.gson.toJson(ex.blockList)
        )
//        }

//        if (ex.rE.isNotEmpty()) {
//            LogUtil.d("SyncSt", "getSyncSp: ${ex}")
        SPTools.setString(
            "${ex.packageName}_${ex.type}_rE",
            BasicApp.gson.toJson(ex.rE)
        )
//        }
    }
}