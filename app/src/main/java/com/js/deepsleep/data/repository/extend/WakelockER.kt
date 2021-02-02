package com.js.deepsleep.data.repository.extend

import com.js.deepsleep.data.db.dao.ExtendDao

class WakelockER(extendDao: ExtendDao) : ER(extendDao) {
    override val type = "wakelock"
}