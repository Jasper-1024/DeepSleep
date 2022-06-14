package com.js.deepsleep.data.repository.syncsp

import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend
import kotlinx.coroutines.flow.Flow

interface SyncSpRepo {
    suspend fun getAllAppSt(): Flow<List<AppSt>>
    suspend fun getAllAppStEx(): Flow<List<Extend>>
}