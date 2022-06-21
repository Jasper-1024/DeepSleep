package com.js.deepsleep.ui.app

import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.js.deepsleep.BasicApp
import com.js.deepsleep.R
import com.js.deepsleep.tools.getSetting
import com.js.deepsleep.data.db.entity.AppInfo
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.ui.databinding.item.BaseItemHandle

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

        // 拓展功能是否开启
        if (getSetting("ExtendEnable")) {
            // 跳转 extend
            val direction = AppFragmentDirections.actionAppFragmentToExtendFragment(
                appInfo.packageName,
                appInfo.label
            )
            view.findNavController().navigate(direction)
        }
    }

    fun onLongClick(view: View, appInfo: AppInfo): Boolean {
        // 拓展功能是否开启
        if (getSetting("ExtendEnable")) {
            val direction = AppFragmentDirections.actionAppFragmentToExtendFragment(
                appInfo.packageName,
                appInfo.label
            )
            view.findNavController().navigate(direction)
        } else {
            Toast.makeText(
                BasicApp.context,
                BasicApp.context.getString(R.string.extendEnable),
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }
}