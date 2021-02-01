package com.js.deepsleep.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "extend", primaryKeys = arrayOf("packageName_ex", "type"))
data class Extend(
    @ColumnInfo(name = "packageName_ex")
    var packageName: String = "",
    var type: String = "",
    var allowList: Set<String> = mutableSetOf(),
    var blockList: Set<String> = mutableSetOf(),
    var rE: Set<String> = mutableSetOf()
)
