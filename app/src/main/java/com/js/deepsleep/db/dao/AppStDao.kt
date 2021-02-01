package com.js.deepsleep.db.dao

import androidx.room.*
import com.js.deepsleep.db.entity.AppSt
import kotlinx.coroutines.flow.Flow

@Dao
interface AppStDao {
    @Query("select * from appSt")
    fun loadAppSts(): Flow<List<AppSt>>

    @Query("select * from appSt where packageName_st = :packageName")
    fun loadAppSt(packageName: String): Flow<AppSt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appSts: List<AppSt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appSt: AppSt)

    @Delete
    suspend fun delete(appSts: List<AppSt>)

    @Delete
    suspend fun delete(appSt: AppSt)

    // for test
    @Query("select * from appSt")
    suspend fun AppSts(): List<AppSt>

    @Query("select * from appSt where packageName_st = :packageName")
    suspend fun AppSt(packageName: String): AppSt
}