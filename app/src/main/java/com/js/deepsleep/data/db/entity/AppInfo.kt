package com.js.deepsleep.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// package 信息
@Entity(tableName = "appInfo")
data class AppInfo(
    @PrimaryKey
    var packageName: String = "",
    var uid: Int = 0,
    var label: String = "",
    var icon: Int = 0,
    var system: Boolean = false,
    var enabled: Boolean = false,
    var persistent: Boolean = false,
    var processName: String = ""
)