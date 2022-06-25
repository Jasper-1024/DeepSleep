package com.js.deepsleep.data.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.js.deepsleep.data.db.AppDatabase
import com.js.deepsleep.data.db.data.TestData
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExtendDaoTest {
    private lateinit var extendDao: ExtendDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        extendDao = db.extendDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test() {

        runBlocking {
            extendDao.insert(TestData.extends)
        }

        val tmps = runBlocking {
            extendDao.Extends()
        }

        Assert.assertEquals(tmps.size, 3)
        Assert.assertEquals(tmps[0], TestData.extends[0])
        Assert.assertEquals(tmps[1].allowList.size, 1)
        Assert.assertEquals(tmps[1].blockList.size, 2)
        Assert.assertEquals(tmps[1].rE.size, 3)

        runBlocking { extendDao.insert(TestData.extend) }

        val tmp = runBlocking { extendDao.getExtend(TestData.packageName, TestData.wakelock) }

        Assert.assertEquals(tmp, TestData.extend)
//        runBlocking {
//            appStDao.insert(TestData.appSts)
//        }
//
//        val tmps = runBlocking { appStDao.AppSts() }
//
//        Assert.assertEquals(tmps[0], TestData.appSts[0])
//        Assert.assertEquals(tmps.size, 3)
//
//        runBlocking { appStDao.insert(TestData.appSt) }
//        val tmp = runBlocking { appStDao.AppSt(TestData.packageName) }
//        Assert.assertEquals(tmp, TestData.appSt)
    }
}