package com.js.deepsleep.data.repository.extend

import com.js.deepsleep.base.Type
import com.js.deepsleep.data.db.dao.ExtendDao
import com.js.deepsleep.data.db.entity.Extend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ER(private val extendDao: ExtendDao, private val type: Type) : ExtendRepo {

    override fun getExtend(packageName: String): Flow<Extend> {
//        LogUtil.d("test13", "$type")
        return extendDao.loadExtend(packageName, type.value).map {
            it ?: Extend(packageName, type.value).apply { extendDao.insert(this) }
        }
    }

    override suspend fun setExtend(extend: Extend) {
//        LogUtil.d("test12", "$extend")
        extendDao.insert(extend)
    }
}