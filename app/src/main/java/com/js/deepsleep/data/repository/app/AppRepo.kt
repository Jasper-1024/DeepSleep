package com.js.deepsleep.data.repository.app

import com.js.deepsleep.data.db.entity.App
import com.js.deepsleep.data.db.entity.AppSt
import kotlinx.coroutines.flow.Flow

interface AppRepo {
    fun getApps(): Flow<List<App>>
    suspend fun setAppSt(appSt: AppSt)
    suspend fun syncAppList()
}