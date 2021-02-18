package com.js.deepsleep.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend

@Dao
interface BackupDao {
    @Query("select * from appSt")
    fun backupAppSts(): List<AppSt>

    @Query("select * from extend")
    fun backupExtends(): List<Extend>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun restoreAppSts(appSts: List<AppSt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun restoreExtends(extends: List<Extend>)
}