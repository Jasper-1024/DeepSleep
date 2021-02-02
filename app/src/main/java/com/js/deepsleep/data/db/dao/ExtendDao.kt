package com.js.deepsleep.data.db.dao

import androidx.room.*
import com.js.deepsleep.data.db.entity.Extend
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtendDao {
    @Query("select * from extend where packageName_ex = :packageName and type = :type")
    fun loadExtend(packageName: String, type: String): Flow<Extend>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(extend: Extend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(extends: List<Extend>)

    @Delete
    suspend fun delete(extend: Extend)

    @Delete
    suspend fun delete(extends: List<Extend>)

    // for test
    @Query("select * from extend where packageName_ex = :packageName and type = :type")
    suspend fun Extend(packageName: String, type: String): Extend

    @Query("select * from extend")
    suspend fun Extends(): List<Extend>
}