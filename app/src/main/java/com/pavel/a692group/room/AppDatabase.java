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

    private static final String DB_NAME = "user3.db";
    //private static AppDatabase INSTANCE;

    public abstract UserDao getUserDao();

    /*public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                            .build();
        }
        return INSTANCE;
        //Для обращения к бд в программе: AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
    }*/

    public static AppDatabase createPersistentDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                .build();
    }
}
