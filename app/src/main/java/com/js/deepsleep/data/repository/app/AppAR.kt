package com.js.deepsleep.data.repository.app

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.collection.ArrayMap
import com.js.deepsleep.BasicApp
import com.js.deepsleep.base.LogUtil
import com.js.deepsleep.data.db.dao.AppDao
import com.js.deepsleep.data.db.dao.AppInfoDao
import com.js.deepsleep.data.db.dao.AppStDao
import com.js.deepsleep.data.db.entity.App
import com.js.deepsleep.data.db.entity.AppInfo
import com.js.deepsleep.data.db.entity.AppSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppAR(
    private val appDao: AppDao,
    private val appInfoDao: AppInfoDao,
    private val appStDao: AppStDao
) : AppRepo {

    private val pm: PackageManager = BasicApp.context.packageManager

    override fun getApps(): Flow<List<App>> {
        return appDao.loadApps().map { list ->
            list.map {
                if (it.st == null) {
                    it.st = AppSt(it.info.packageName)
                }
                it
            }
        }
    }

    override suspend fun setAppSt(appSt: AppSt) {
        appStDao.insert(appSt)
    }

    override suspend fun syncAppList() = withContext(Dispatchers.Default) {
        val dbAppInfos = getDBAppInfos()//db AppInfos
        val sysAppInfos = getSysAppInfos()//system AppInfos

        //取差集更新删除
        insertAll(sysAppInfos.keys subtract dbAppInfos.keys, sysAppInfos)
        deleteAll(dbAppInfos.keys subtract sysAppInfos.keys, dbAppInfos)
    }

    /**db 插入新应用*/
    private suspend fun insertAll(
        packageNames: Set<String>,
        sysAppInfos: ArrayMap<String, AppInfo>
    ) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            sysAppInfos.filter { it.key in packageNames }.let {
                appInfoDao.insert(it.values as MutableCollection<AppInfo>)
            }
        }
    }

    /**db 删除卸载应用*/
    private suspend fun deleteAll(
        packageNames: Set<String>,
        dbAppInfos: ArrayMap<String, AppInfo>
    ) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            dbAppInfos.filter { it.key in packageNames }.let {
                appInfoDao.deleteAll(it.values as MutableCollection<AppInfo>)
            }
        }
    }

    // 获取全部 system AppInfos
    @SuppressLint("QueryPermissionsNeeded")
    private suspend fun getSysAppInfos(): ArrayMap<String, AppInfo> =
        withContext(Dispatchers.IO) {
            val sysAppInfo = ArrayMap<String, AppInfo>()

            LogUtil.d("test2", "${pm.getInstalledApplications(0).size}")

            pm.getInstalledApplications(0).forEach {
                sysAppInfo[it.packageName] = getSysAppInfo(it)
            }

            return@withContext sysAppInfo
        }

    // 获取全部数据库 AppInfos
    private suspend fun getDBAppInfos(): ArrayMap<String, AppInfo> =
        withContext(Dispatchers.IO) {
            val dbAppInfos = ArrayMap<String, AppInfo>()
            appInfoDao.loadAppInfos().forEach {
                dbAppInfos[it.packageName] = it
            }
            return@withContext dbAppInfos
        }

    //获取单个 AppInfo
    private fun getSysAppInfo(ai: ApplicationInfo): AppInfo {
        val easting = pm.getApplicationEnabledSetting(ai.packageName)
        val enabled = ai.enabled &&
                (easting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ||
                        easting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
        val persistent =
            ai.flags and ApplicationInfo.FLAG_PERSISTENT != 0 || "android" == ai.packageName
        val system = ai.flags and
                (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

        return AppInfo(
            ai.packageName,
            ai.uid,
            pm.getApplicationLabel(ai) as String,
            ai.icon,
            system,
            enabled,
            persistent,
            ai.processName
        )
    }
}