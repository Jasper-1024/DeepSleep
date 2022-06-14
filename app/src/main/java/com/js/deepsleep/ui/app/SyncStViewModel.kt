package com.js.deepsleep.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.base.LogUtil
import com.js.deepsleep.data.repository.syncsp.SyncSpRepo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class SyncStViewModel : ViewModel(), KoinComponent {
    private val syncSPR: SyncSpRepo by inject(named("syncSpR"))

    fun syncSp() {
        viewModelScope.launch {
            syncSPR.getAllAppSt().collect() {
                LogUtil.d("SyncStViewModel", "getSyncSp: ${it.size}")
            }
        }
    }
}