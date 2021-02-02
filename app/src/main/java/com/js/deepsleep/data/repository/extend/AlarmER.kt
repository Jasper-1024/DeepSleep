package com.js.deepsleep.data.repository.extend

import com.js.deepsleep.data.db.dao.ExtendDao

class AlarmER(extendDao: ExtendDao) : ER(extendDao) {
    override val type = "alarm"
}