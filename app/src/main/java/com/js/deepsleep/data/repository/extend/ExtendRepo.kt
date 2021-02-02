package com.js.deepsleep.data.repository.extend

import com.js.deepsleep.data.db.entity.Extend
import kotlinx.coroutines.flow.Flow

interface ExtendRepo {
    fun getExtend(packageName: String): Flow<Extend>
    suspend fun setExtend(extend: Extend)
}