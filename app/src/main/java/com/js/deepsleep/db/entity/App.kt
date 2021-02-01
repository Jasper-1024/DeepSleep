package com.js.deepsleep.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class App(
    @Embedded
    var info: AppInfo,
    @Relation(
        parentColumn = "packageName",
        entityColumn = "packageName_st"
    )
    var st: AppSt
)
