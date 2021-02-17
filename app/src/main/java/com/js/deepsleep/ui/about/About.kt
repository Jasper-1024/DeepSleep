package com.js.deepsleep.ui.about

import com.js.deepsleep.BasicApp
import com.js.deepsleep.BuildConfig
import com.js.deepsleep.R

data class About(
    var app_name: String = BasicApp.context.getString(R.string.app_name),
    var app_version: String = BuildConfig.VERSION_NAME,
    var source: String = BasicApp.context.getString(R.string.SourceCodeUrl),
    var license: String = "GNU General Public License version 3",
    var contact: String = BasicApp.context.getString(R.string.help_contact)
)
