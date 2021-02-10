package com.js.deepsleep.ui.mainactivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.js.deepsleep.base.AppType
import com.js.deepsleep.base.Sort

class MainViewModel : ViewModel() {
    // App 类型
    val type: MutableLiveData<AppType> by lazy {
        MutableLiveData<AppType>(AppType.User)
    }

    val sort: MutableLiveData<Sort> by lazy {
        MutableLiveData<Sort>(Sort.Name)
    }

    val query: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
}