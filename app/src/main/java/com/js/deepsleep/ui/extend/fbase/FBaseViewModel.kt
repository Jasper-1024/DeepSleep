package com.js.deepsleep.ui.extend.fbase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.data.db.entity.Extend
import com.js.deepsleep.data.repository.extend.ExtendRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FBaseViewModel(packageName: String, private val extendRepo: ExtendRepo) : ViewModel() {

    var extend = extendRepo.getExtend(packageName).asLiveData()

    fun setEx(extend: Extend) {
        viewModelScope.launch(Dispatchers.IO) {
            extendRepo.setExtend(extend)
        }
    }
}