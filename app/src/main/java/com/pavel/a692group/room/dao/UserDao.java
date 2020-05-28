package com.pavel.a692group.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pavel.a692group.room.entity.User;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM " + User.TABLE)
    Flowable<List<User>> getAll();

    @Query("SELECT * FROM " + User.TABLE + " WHERE " + User.COLUMN_ID + " = :id")
    Flowable<List<User>> getById(long id);

    @Query("SELECT DISTINCT " + User.COLUMN_GROUP + " FROM " + User.TABLE)
    Flowable<List<String>> getAllGroup();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    /*@Update
    int update(User user);*/

    @Delete
    void delete(User user);
}
