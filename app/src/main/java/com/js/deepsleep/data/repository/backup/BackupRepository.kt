package com.js.deepsleep.data.repository.backup

import com.js.deepsleep.data.db.dao.BackupDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackupRepository(private val backupDao: BackupDao) {
    //返回 AppBackup
    suspend fun getBackup(extendEnable: Boolean): AppBackup = withContext(Dispatchers.IO) {
        AppBackup().apply {
            this.appSts = backupDao.backupAppSts()
            if (extendEnable) this.extends = backupDao.backupExtends()
        }
    }

    //恢复 AppBackup
    suspend fun restoreBackup(appBackup: AppBackup) = withContext(Dispatchers.IO) {
        if (appBackup.appSts.isNotEmpty()) backupDao.restoreAppSts(appBackup.appSts)
        if (appBackup.extends.isNotEmpty()) backupDao.restoreExtends(appBackup.extends)
    }
}