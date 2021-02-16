package com.js.deepsleep.ui.app

import android.view.View
import androidx.navigation.findNavController
import com.js.deepsleep.data.db.entity.AppInfo
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.ui.databinding.item.BaseItemHandle
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class HandleApp(private val appViewModel: AppViewModel) : BaseItemHandle() {
    // 保存 Appst
    fun save(appSt: AppSt) {
        appViewModel.saveSt(appSt)
    }

    // 保存 load
    fun load(itemApp: ItemApp) {
        appViewModel.setLoad(itemApp)
    }

    fun onClick(view: View, appInfo: AppInfo) {
        val direction = AppFragmentDirections.actionAppFragmentToExtendFragment(
            appInfo.packageName,
            appInfo.label
        )
        view.findNavController().navigate(direction)
    }

//    fun onLongClick(view: View, appInfo: AppInfo): Boolean {
//        val direction = AppFragmentDirections.actionAppFragmentToExtendFragment(
//            appInfo.packageName,
//            appInfo.label
//        )
//        view.findNavController().navigate(direction)
//        return true
//    }
}