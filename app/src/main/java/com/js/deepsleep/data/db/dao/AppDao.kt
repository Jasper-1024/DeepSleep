package com.js.deepsleep.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.js.deepsleep.data.db.entity.App
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // for  appFragment
    @Transaction
    @Query("select * from appInfo")
    fun loadApps(): Flow<List<App>>

    @Transaction
    @Query("select * from appInfo where packageName = :packageName")
    fun loadApp(packageName: String): Flow<App>
}