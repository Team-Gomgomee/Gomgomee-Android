package com.konkuk.gomgomee.data.local.dao

import androidx.room.*
import com.konkuk.gomgomee.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserEntity>>
    
    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Delete
    suspend fun deleteUser(user: UserEntity)
    
    @Query("SELECT * FROM user WHERE id = :id AND password = :password")
    suspend fun login(id: String, password: String): UserEntity?

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

    @Query("SELECT EXISTS(SELECT 1 FROM user WHERE id = :id)")
    suspend fun isIdExists(id: String): Boolean
} 