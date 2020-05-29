package com.pavel.a692group.data.repository

import androidx.lifecycle.LiveData
import com.pavel.a692group.room.dao.UserDao

class UsersRepository(
    private val userDao: UserDao
) {
    fun getGroups(): LiveData<List<String?>?> {
        return userDao.allGroups
    }
}

