package com.js.deepsleep.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "appSt")
data class AppSt(
    @PrimaryKey
    @ColumnInfo(name = "packageName_st")
    var packageName: String = "",
    @Ignore
    var flag: Boolean = false,//仅标记应用是否有限制
    var wakelock: Boolean = false,
    var alarm: Boolean = false,
    var server: Boolean = false
)
