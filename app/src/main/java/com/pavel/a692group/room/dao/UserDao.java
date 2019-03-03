package com.pavel.a692group.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
