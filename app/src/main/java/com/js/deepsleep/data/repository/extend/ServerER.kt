package com.js.deepsleep.data.repository.extend

import com.js.deepsleep.data.db.dao.ExtendDao

class ServerER(extendDao: ExtendDao) : ER(extendDao) {
    override val type = "server"
}