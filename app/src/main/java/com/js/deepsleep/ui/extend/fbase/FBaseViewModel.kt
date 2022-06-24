package com.js.deepsleep.ui.extend.fbase

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.deepsleep.BasicApp
import com.js.deepsleep.R
import com.js.deepsleep.data.db.entity.Extend
import com.js.deepsleep.data.repository.app.AppRepo
import com.js.deepsleep.data.repository.extend.ExtendRepo
import com.js.deepsleep.tools.XProviderMethods
import com.js.deepsleep.tools.callStopApp
import com.js.deepsleep.tools.getSetting
import com.js.deepsleep.tools.getURI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named


class FBaseViewModel(packageName: String, private val extendRepo: ExtendRepo) : ViewModel(),
    KoinComponent {

    private val appAR: AppRepo by inject(named("AppR"))


    var extend = extendRepo.getExtend(packageName).asLiveData()

    fun setEx(extend: Extend) {
        viewModelScope.launch(Dispatchers.IO) {
            extendRepo.setExtend(extend)
            if (getSetting("ForceStopEnable"))
                stopApp(extend.packageName)
        }
    }

    // call system force stop app
    private suspend fun stopApp(packageName: String) {
        val appInfo = appAR.getAppInfo(packageName)

        if (appInfo.persistent || appInfo.system) {// if app is system or persistent
            return
        }
        val result = callStopApp(appInfo.packageName, appInfo.uid)
        withContext(Dispatchers.Main) {
            isSuccess(result)
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
}