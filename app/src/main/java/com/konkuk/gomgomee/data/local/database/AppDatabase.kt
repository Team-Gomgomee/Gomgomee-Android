package com.konkuk.gomgomee.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.konkuk.gomgomee.data.local.dao.ChecklistItemDao
import com.konkuk.gomgomee.data.local.dao.ChecklistResultDao
import com.konkuk.gomgomee.data.local.dao.TestQuestionDao
import com.konkuk.gomgomee.data.local.dao.TestSessionDao
import com.konkuk.gomgomee.data.local.dao.UserDao
import com.konkuk.gomgomee.data.local.entity.ChecklistItemEntity
import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
import com.konkuk.gomgomee.data.local.entity.TestQuestionEntity
import com.konkuk.gomgomee.data.local.entity.TestSessionEntity
import com.konkuk.gomgomee.data.local.entity.UserEntity
import com.konkuk.gomgomee.data.local.entity.InstitutionEntity
import com.konkuk.gomgomee.data.local.dao.InstitutionDao
import com.konkuk.gomgomee.data.local.dao.FavoriteInstitutionDao
import com.konkuk.gomgomee.data.local.entity.FavoriteInstitutionEntity

@Database(
    entities = [
        UserEntity::class,
        ChecklistItemEntity::class,
        ChecklistResultEntity::class,
        TestQuestionEntity::class,
        TestSessionEntity::class,
        InstitutionEntity::class,
        FavoriteInstitutionEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun checklistItemDao(): ChecklistItemDao
    abstract fun checklistResultDao(): ChecklistResultDao
    abstract fun testQuestionDao(): TestQuestionDao
    abstract fun testSessionDao(): TestSessionDao
    abstract fun institutionDao(): InstitutionDao
    abstract fun favoriteInstitutionDao(): FavoriteInstitutionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gomgomee_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 