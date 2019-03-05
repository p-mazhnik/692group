package com.pavel.a692group.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */

@Entity(tableName = User.TABLE)
public class User implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = User.COLUMN_ID)
    private int mId;

    @ColumnInfo(name = User.COLUMN_NAME)
    private String mName;

    @ColumnInfo(name = User.COLUMN_GROUP)
    private String mGroup;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getIdToString() {
        return ("" + mId);
    }

    public void setIdFromString(String str){
        mId = -1;
        try {
            mId = Integer.parseInt(str);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public void updateUser(User user){
        mId = user.getId();
        mName = user.getName();
        mGroup = user.getGroup();
    }

    @Ignore
    public static final String TABLE = "users"; // название таблицы в бд
    // названия столбцов
    @Ignore
    public static final String COLUMN_ID = "_id";
    @Ignore
    public static final String COLUMN_LINK = "_link";
    @Ignore
    public static final String COLUMN_SURNAME = "surname";
    @Ignore
    public static final String COLUMN_NAME = "name";
    @Ignore
    public static final String COLUMN_SECOND_NAME = "second_name";
    @Ignore
    public static final String COLUMN_GROUP = "_group";
}
