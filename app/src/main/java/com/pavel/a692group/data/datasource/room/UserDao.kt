package com.pavel.a692group.data.datasource.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pavel.a692group.data.entity.User

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */
@Dao
interface UserDao {
    @get:Query("SELECT * FROM " + User.TABLE)
    val all: LiveData<List<User>>

    @Query("SELECT * FROM " + User.TABLE + " WHERE " + User.COLUMN_ID + " = :id")
    suspend fun getById(id: Long): User?

    @get:Query("SELECT DISTINCT " + User.COLUMN_GROUP + " FROM " + User.TABLE)
    val allGroups: LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    /*@Update
    int update(User user);*/
    @Delete
    suspend fun delete(user: User)
}

