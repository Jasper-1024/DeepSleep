package com.js.deepsleep.data.db.dao

import androidx.room.*
import com.js.deepsleep.data.db.entity.AppInfo

@Dao
interface AppInfoDao {
    @Query("select * from appInfo")
    fun loadAppInfos(): List<AppInfo>

    @Query("select * from appInfo where packageName = :packageName")
    fun loadAppInfo(packageName: String): AppInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfos: MutableCollection<AppInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo: AppInfo)

    @Delete
    suspend fun deleteAll(appInfos: MutableCollection<AppInfo>)

    @Delete
    suspend fun delete(appInfo: AppInfo)
}