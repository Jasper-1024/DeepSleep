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
class AppStDaoTest {

    private lateinit var appStDao: AppStDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        appStDao = db.appStDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testAlarm() {
        runBlocking {
            appStDao.insert(TestData.appSts)
        }

        val tmps = runBlocking { appStDao.AppSts() }

        Assert.assertEquals(tmps[0], TestData.appSts[0])
        Assert.assertEquals(tmps.size, 3)

        runBlocking { appStDao.insert(TestData.appSt) }
        val tmp = runBlocking { appStDao.AppSt(TestData.packageName) }
        Assert.assertEquals(tmp, TestData.appSt)
    }
}