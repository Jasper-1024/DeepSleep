package com.js.deepsleep.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "extend", primaryKeys = ["packageName_ex", "type"])
data class Extend(
    @ColumnInfo(name = "packageName_ex")
    var packageName: String = "",
    var type: String = "",
    var allowList: Set<String> = mutableSetOf(),
    var blockList: Set<String> = mutableSetOf(),
    var rE: Set<String> = mutableSetOf()
) : Serializable
