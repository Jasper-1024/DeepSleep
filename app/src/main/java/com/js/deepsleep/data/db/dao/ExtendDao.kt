package com.js.deepsleep.data.db.dao

import androidx.room.*
import com.js.deepsleep.data.db.entity.Extend
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtendDao {

    //    @Query("select * from extend where type = :type")
    //    fun loadExtends(type: String): Flow<Extend>
    @Query("select * from extend")
    fun loadExtends(): Flow<List<Extend>>

    @Query("select * from extend where packageName_ex = :packageName and type = :type")
    fun loadExtend(packageName: String, type: String): Flow<Extend?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(extend: Extend)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(extends1: List<Extend>)

    @Delete
    suspend fun delete(extend: Extend)

    @Delete
    suspend fun delete(extends1: List<Extend>)

    @Query("select * from extend where packageName_ex = :packageName and type = :type")
    fun getExtend(packageName: String, type: String): Extend?

    @Query("select * from extend")
    suspend fun Extends(): List<Extend>
}