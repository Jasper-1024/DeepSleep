package com.js.deepsleep.data.db

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private val TEST_DB = "migration-test"

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
                    INSERT INTO appSt_tmp(packageName_st, flag, wakelock, alarm, service, sync)
                    SELECT packageName_st, flag, wakelock, alarm, service, sync
                    FROM appSt
                """.trimIndent()
            )
            database.execSQL("DROP TABLE appSt")
            database.execSQL("ALTER TABLE appSt_tmp RENAME TO appSt")
        }
    }


    private val ALL_MIGRATIONS = arrayOf(
        MIGRATION_1_2
    )


    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        var db = helper.createDatabase(TEST_DB, 1).apply {
            // db has schema version 1. insert some data using SQL queries.
            // You cannot use DAO classes because they expect the latest schema.
//            execSQL(...)


            // Prepare for the next version.
            close()
        }
//
//        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
    }


    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        // Create earliest version of the database.
        helper.createDatabase(TEST_DB, 1).apply {
            close()
        }

        // Open latest version of the database. Room will validate the schema
        // once all migrations execute.
        Room.databaseBuilder<AppDatabase>(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java, TEST_DB
        ).addMigrations(*ALL_MIGRATIONS).build().apply {
            openHelper.writableDatabase
            close()
        }
    }

}