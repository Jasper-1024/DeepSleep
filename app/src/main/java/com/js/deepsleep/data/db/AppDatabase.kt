package com.js.deepsleep.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.js.deepsleep.data.db.converter.Converters
import com.js.deepsleep.data.db.dao.*
import com.js.deepsleep.data.db.entity.AppInfo
import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend

@Database(
    entities = [AppInfo::class, AppSt::class, Extend::class],
    version = 2
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
            context.applicationContext, AppDatabase::class.java, DATABASE_NAME
        ).addMigrations(MIGRATION_1_2)
            .build()


        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS appSt_tmp (
                        `packageName_st` TEXT NOT NULL, 
                        `flag` INTEGER NOT NULL, 
                        `wakelock` INTEGER NOT NULL, 
                        `alarm` INTEGER NOT NULL, 
                        `service` INTEGER NOT NULL, 
                        `sync` INTEGER NOT NULL, 
                        `broadcast` INTEGER NOT NULL DEFAULT 0, 
                        PRIMARY KEY(`packageName_st`))
                    """.trimMargin()
                )
                database.execSQL(
                    """
                    INSERT INTO appSt_tmp (packageName_st, flag, wakelock, alarm, service, sync)
                    SELECT packageName_st, flag, wakelock, alarm, service, sync
                    FROM appSt
                    """.trimIndent()
                )
                database.execSQL("DROP TABLE appSt")
                database.execSQL("ALTER TABLE appSt_tmp RENAME TO appSt")
            }
        }
    }
}