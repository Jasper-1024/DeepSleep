package com.js.deepsleep.data.repository.syncsp

import com.js.deepsleep.data.db.dao.AppStDao
import com.js.deepsleep.data.db.dao.ExtendDao
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend
import kotlinx.coroutines.flow.Flow

class SyncSp(private val appStDao: AppStDao, private val extendDao: ExtendDao) : SyncSpRepo {
    override suspend fun getAllAppSt(): Flow<List<AppSt>> {
        return appStDao.loadAppSts()
    }

    override suspend fun getAllAppStEx(): Flow<List<Extend>> {
        return extendDao.loadExtends()
    }

}