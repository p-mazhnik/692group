package com.pavel.a692group;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by p.mazhnik on 30.12.2017.
 * to 692group
 */

class DBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = null; // полный путь к базе данных
    private static String DB_NAME = "692group.db";
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "All_users"; // название таблицы в бд
    // названия столбцов
    static final String COLUMN_ID = "_id";
    static final String COLUMN_LINK = "_link";
    static final String COLUMN_SURNAME = "surname";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_SECOND_NAME = "second_name";
    static final String COLUMN_GROUP = "_group";
    static final String[] COLUMNS = new String[]{COLUMN_ID, COLUMN_LINK, COLUMN_SURNAME, COLUMN_NAME, COLUMN_SECOND_NAME, COLUMN_GROUP};
    private Context myContext;

    DBHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        //TODO
    }

    void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.d("DatabaseHelper", ex.getMessage());
        }
    }
    public SQLiteDatabase open()throws SQLException {

        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
/*
Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE + " where " + DBHelper.COLUMN_GROUP
                        + "=? OR " + DBHelper.COLUMN_GROUP + "=?", new String[]{"admin", "step"});
 */