package com.js.deepsleep.data.repository.extend

import com.js.deepsleep.data.db.entity.Extend
import com.js.deepsleep.tools.Type
import kotlinx.coroutines.flow.Flow

interface ExtendRepo {
    fun getExtend(packageName: String): Flow<Extend>
    fun getExtend(packageName: String, type: Type): Extend?
    suspend fun setExtend(extend: Extend)
}