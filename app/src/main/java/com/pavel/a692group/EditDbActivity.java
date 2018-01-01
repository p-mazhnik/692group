package com.pavel.a692group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by p.mazhnik on 31.12.2017. 23:50
 * to 692group
 */

public class EditDbActivity extends AppCompatActivity {
    private GridView mGridView;
    private FloatingActionButton mNewButton;

    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor mCursor;

    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_db);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mGridView = (GridView) findViewById(R.id.edit_db_grid_view);
        mNewButton = (FloatingActionButton) findViewById(R.id.edit_db_floatingActionButton);

        databaseHelper = new DBHelper(this);
        db = databaseHelper.open();

        mCursor = db.rawQuery("select * from " + DBHelper.TABLE + " ORDER BY " + DBHelper.COLUMN_SURNAME, null);
        String[] fromColumns = new String[]{DBHelper.COLUMN_SURNAME, DBHelper.COLUMN_NAME};

        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                mCursor, fromColumns, new int[]{android.R.id.text1, android.R.id.text2}, 0);

        mGridView.setAdapter(userAdapter);
        //mGridView.setOnItemSelectedListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                /*String str = "" + position + " " + id;
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();*/
                Intent i = new Intent(getApplicationContext(), EditUserActivity.class);
                i.putExtra(DBHelper.COLUMN_ID, id);
                i.putExtra(getString(R.string.edit_key), R.string.edit_key_old);
                db.close(); //нужно ли?
                startActivity(i);
            }
        });

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditUserActivity.class);
                i.putExtra(getString(R.string.edit_key), R.string.edit_key_new);
                db.close();
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mCursor.close();
        db.close();
    }

/*    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        mSelectText.setText("Выбранный элемент: " + mAdapter.getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mSelectText.setText("Выбранный элемент: ничего");
    }*/
}
