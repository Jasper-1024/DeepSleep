package com.js.deepsleep.ui.about

import androidx.lifecycle.ViewModel
import com.js.deepsleep.BasicApp

class AboutViewModel : ViewModel() {
    var about = About()

    fun test() {
        AliPay(BasicApp.context).jumpAlipay()
    }
}