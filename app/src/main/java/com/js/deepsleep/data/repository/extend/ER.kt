package com.js.deepsleep.data.repository.extend

import com.js.deepsleep.data.db.dao.ExtendDao
import com.js.deepsleep.data.db.entity.Extend
import kotlinx.coroutines.flow.Flow

open class ER(private val extendDao: ExtendDao) : ExtendRepo {
    open val type = "wakelock"

    override fun getExtend(packageName: String): Flow<Extend> {
        return extendDao.loadExtend(packageName, type)
    }

    override suspend fun setExtend(extend: Extend) {
        extendDao.insert(extend)
    }
}