package com.konkuk.gomgomee.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.konkuk.gomgomee.data.local.dao.ChecklistItemDao
import com.konkuk.gomgomee.data.local.dao.ChecklistResultDao
import com.konkuk.gomgomee.data.local.dao.TestQuestionDao
import com.konkuk.gomgomee.data.local.dao.UserDao
import com.konkuk.gomgomee.data.local.entity.ChecklistItemEntity
import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
import com.konkuk.gomgomee.data.local.entity.TestQuestionEntity
import com.konkuk.gomgomee.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ChecklistItemEntity::class,
        ChecklistResultEntity::class,
        TestQuestionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun checklistItemDao(): ChecklistItemDao
    abstract fun checklistResultDao(): ChecklistResultDao
    abstract fun testQuestionDao(): TestQuestionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gomgomee_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 