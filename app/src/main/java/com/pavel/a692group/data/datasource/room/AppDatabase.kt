package com.pavel.a692group.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pavel.a692group.room.dao.UserDao
import com.pavel.a692group.room.entity.User

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description: room database
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}

