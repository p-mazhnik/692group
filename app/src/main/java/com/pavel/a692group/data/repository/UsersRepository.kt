package com.pavel.a692group.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.pavel.a692group.room.dao.UserDao
import com.pavel.a692group.room.entity.User

class UsersRepository(
    private val userDao: UserDao
) {
    fun getGroups(): LiveData<List<String>> {
        return userDao.allGroups
    }

    fun getUsers(): LiveData<List<User>> {
        return userDao.all
    }

    suspend fun getUserById(id: Long): User? {
        Log.d("UserRepository", "getUserById user_id: $id")
        return userDao.getById(id)
    }

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }
}

