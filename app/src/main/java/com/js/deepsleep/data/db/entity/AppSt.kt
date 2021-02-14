package com.js.deepsleep.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "appSt")
data class AppSt(
    @PrimaryKey
    @ColumnInfo(name = "packageName_st")
    var packageName: String = "",
    var flag: Boolean = false,//仅标记应用是否有限制
    var wakelock: Boolean = false,
    var alarm: Boolean = false,
    var service: Boolean = false
) : Serializable
