package com.js.deepsleep.ui.help

import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.js.deepsleep.BasicApp
import com.js.deepsleep.BuildConfig
import com.js.deepsleep.R

data class Help(
    var app_name: String = BasicApp.context.getString(R.string.app_name),
    var app_version: String = BuildConfig.VERSION_NAME,
    var instructions: Spanned = HtmlCompat.fromHtml(
        BasicApp.context.getString(R.string.help_instructions),
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ),
    var license: String = "",
    var contact: String = BasicApp.context.getString(R.string.help_contact)
//    var issue:String = "https://github.com/Jasper-1024/NoWakeLock/issues",
//    var author:String = "JasperHale",
//    var email: String = "ljy087621@gmail.com"
)