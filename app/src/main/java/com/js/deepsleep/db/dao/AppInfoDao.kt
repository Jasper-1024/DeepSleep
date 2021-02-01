package com.js.deepsleep.db.dao

import androidx.room.*
import com.js.deepsleep.db.entity.AppInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDao {
    @Query("select * from appInfo")
    fun loadAppInfos(): Flow<List<AppInfo>>

    @Query("select * from appInfo where packageName = :packageName")
    fun loadAppInfo(packageName: String): Flow<AppInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfos: List<AppInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo: AppInfo)

    @Delete
    suspend fun delete(appInfos: List<AppInfo>)

    @Delete
    suspend fun delete(appInfo: AppInfo)
}