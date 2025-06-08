package com.konkuk.gomgomee.data.repository

import com.konkuk.gomgomee.data.local.dao.UserDao
import com.konkuk.gomgomee.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    val allUsers: Flow<List<UserEntity>> = userDao.getAllUsers()
    
    suspend fun insert(user: UserEntity) {
        userDao.insertUser(user)
    }
    
    suspend fun update(user: UserEntity) {
        userDao.updateUser(user)
    }
    
    suspend fun delete(user: UserEntity) {
        userDao.deleteUser(user)
    }
    
    suspend fun login(id: String, password: String): UserEntity? {
        return userDao.login(id, password)
    }
    
    fun getUserById(userId: String): Flow<UserEntity?> {
        return userDao.getUserById(userId)
    }

    suspend fun isIdExists(id: String): Boolean {
        return userDao.isIdExists(id)
    }
} 