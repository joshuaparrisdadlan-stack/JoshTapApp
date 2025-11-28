package com.parris.joshtap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TrackEntity::class, CardEntity::class, CardTrackJoin::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
                    override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                        // add nfc_token column to cards table
                        database.execSQL("ALTER TABLE cards ADD COLUMN nfc_token TEXT")
                    }
                }

                val inst = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "joshtap.db")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .build()
                INSTANCE = inst
                inst
            }
        }
    }
}
