package com.pavel.a692group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pavel.a692group.request.http.HttpGetRequestTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button new_year_button;

    DBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //System.out.println("Start class MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar(); //logo
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        databaseHelper = new DBHelper(getApplicationContext());
        databaseHelper.create_db();

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startInfoActivity();
            }
        });

        new_year_button = (Button) findViewById(R.id.new_year_button);
        new_year_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = databaseHelper.open();
                Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE, null);
                //SQL_request(cursor, getApplicationContext().getString(R.string.new_year_1));
                SQL_request(cursor, getApplicationContext().getString(R.string.new_year_2));
                cursor.close();
                db.close();
            }
        });
    }

    private void SQL_request(Cursor cursor, String str){
        ArrayList<Integer> _ids = new ArrayList<>();
        if (cursor.moveToFirst()) {
            //System.out.println("ONE ROW");
            int idIndex = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                        /*System.out.println(idIndex);
                        System.out.println(cursor.getInt(idIndex));*/
            do{
                //System.out.println(cursor.getInt(idIndex));
                _ids.add(cursor.getInt(idIndex));
            } while (cursor.moveToNext());
        } else{
            //System.out.println("NON ROW");
            Toast.makeText(this, this.getString(R.string.empty_db), Toast.LENGTH_SHORT).show();
        }
        String URL = new VkAPI.Messages.send(this)
                .user_ids(_ids) //id_vk
                .message(str)
                .build();
        HttpGetRequestTask request = new HttpGetRequestTask(URL, null);
        request.execute(); //выолнение запроса в отдельном потоке
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                startInfoActivity();
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                startInfoActivity();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

    private void startInfoActivity() {
        startActivity(new Intent(this, InfoActivity.class));
    }
}