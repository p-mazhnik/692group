package com.pavel.a692group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by p.mazhnik on 01.01.2018.
 * to 692group
 */

public class EditUserActivity extends AppCompatActivity {
    private TableLayout mTable;
    private Button mOkButton;

    private ArrayList<Pair<TextView, EditText>> mList;

    DBHelper databaseHelper;
    SQLiteDatabase db;
    long taskCount;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        databaseHelper =  new DBHelper(this);
        db = databaseHelper.open();

        mTable = (TableLayout) findViewById(R.id.edit_user_table);
        mOkButton = (Button) findViewById(R.id.edit_user_ok_button);
        mList = new ArrayList<>();

        Intent intent = getIntent();
        int flag = intent.getExtras().getInt(getString(R.string.edit_key));
        if(flag == R.string.edit_key_old) {
            long id = intent.getExtras().getLong(DBHelper.COLUMN_ID);
            createRowsTable(id, flag);
        }
        if(flag == R.string.edit_key_new){
            createRowsTable(0, flag);
            Toast.makeText(this, "new user", Toast.LENGTH_LONG).show();
            //TODO: new user
        }
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDataBase(); //update data
                finish();
            }
        });
    }

    private void createRowsTable(long id, int key){
        if(key != R.string.edit_key_new) {
            mCursor = db.rawQuery("select * from " + DBHelper.TABLE + " where " + DBHelper.COLUMN_ID
                    + "=?", new String[]{id + ""});
        } else {
            mCursor = db.rawQuery("select * from " + DBHelper.TABLE, null);
        }
        taskCount = mCursor.getColumnCount();
        if(mCursor.moveToFirst()) {
            for (int i = mCursor.getColumnIndex(DBHelper.COLUMN_ID); i < taskCount; ++i) {
                TableRow Row = new TableRow(this);//TODO: добавить id или внести их в список
                TextView mTextView = new TextView(this);
                mTextView.setText(DBHelper.COLUMNS[i]);
                Row.addView(mTextView);
                EditText mEditText = new EditText(this);
                if(key != R.string.edit_key_new) mEditText.setText(mCursor.getString(i));
                Row.addView(mEditText);
                mList.add(new Pair<>(mTextView, mEditText));
                mTable.addView(Row);
            }
        }
        mCursor.close();
    }

    private void UpdateDataBase(){
        //TODO
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mCursor.close();
        db.close();
    }
}
