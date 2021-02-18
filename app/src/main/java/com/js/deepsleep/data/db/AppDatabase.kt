package com.js.deepsleep.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.js.deepsleep.data.db.converter.Converters
import com.js.deepsleep.data.db.dao.*
import com.js.deepsleep.data.db.entity.AppInfo
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend

@Database(
    entities = [AppInfo::class, AppSt::class, Extend::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
    abstract fun appInfoDao(): AppInfoDao
    abstract fun appStDao(): AppStDao
    abstract fun extendDao(): ExtendDao
    abstract fun backupDao(): BackupDao

    companion object {
        private const val DATABASE_NAME = "deepSleep"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildInstance(context).also {
                    instance = it
                }
            }

        private fun buildInstance(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}