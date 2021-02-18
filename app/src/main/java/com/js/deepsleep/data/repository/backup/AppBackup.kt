package com.js.deepsleep.data.repository.backup

import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend

data class AppBackup(
    var appSts: List<AppSt> = mutableListOf(),
    var extends: List<Extend> = mutableListOf()
)