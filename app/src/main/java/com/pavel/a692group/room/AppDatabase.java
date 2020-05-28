package com.pavel.a692group.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;
import com.pavel.a692group.room.dao.UserDao;
import com.pavel.a692group.room.entity.User;

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
