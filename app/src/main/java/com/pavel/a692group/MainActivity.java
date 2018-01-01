package com.pavel.a692group;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pavel.a692group.request.http.HttpGetRequestTask;

/**
 * Created by p.mazhnik on 17.11.2017.
 * to 692group
 */

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    //private Button new_year_button; //[v.1.0.1 new_year]
    private static boolean isLogin = false;

    DBHelper databaseHelper;
    private final Time time_start = new Time(Time.getCurrentTimezone());
    private Time time_current = new Time(Time.getCurrentTimezone());

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

        //[v.1.0.1 new_year]
        /*new_year_button = (Button) findViewById(R.id.new_year_button);
        new_year_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = databaseHelper.open();
                Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE, null);
                //new_year_SQL_request(cursor, getApplicationContext().getString(R.string.new_year_1));
                new_year_SQL_request(cursor, getApplicationContext().getString(R.string.new_year_2));
                cursor.close();
                db.close();
            }
        });*/

        //time_current.setToNow();
        /*String str = "" + (time_current.second - time_start.second);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();*/
        //if(!isLogin /*|| (time_current.minute - time_start.minute >= 5)*/) startLoginActivity(); //TODO: включить логинку обратно
    }

    private void new_year_SQL_request(Cursor cursor, String str){ //[v.1.0.1 new_year]
        //ArrayList<Integer> _ids = new ArrayList<>();
        if (cursor.moveToFirst()) {
            //System.out.println("ONE ROW");
            int idIndex = cursor.getColumnIndex(DBHelper.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
            int secondNameIndex = cursor.getColumnIndex(DBHelper.COLUMN_SECOND_NAME);
                        /*System.out.println(idIndex);
                        System.out.println(cursor.getInt(idIndex));*/
            do{
                //System.out.println(cursor.getInt(idIndex));
                String URL = new VkAPI.Messages.send(this)
                        .user_id(cursor.getInt(idIndex)) //id_vk
                        .message(cursor.getString(nameIndex) + " " + cursor.getString(secondNameIndex) + "!\n" + str)
                        .build();
                HttpGetRequestTask request = new HttpGetRequestTask(URL, null);
                request.execute(); //выолнение запроса в отдельном потоке
                //_ids.add(cursor.getInt(idIndex));
            } while (cursor.moveToNext());
        } else{
            //System.out.println("NON ROW");
            Toast.makeText(this, this.getString(R.string.empty_db), Toast.LENGTH_SHORT).show();
        }

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

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //получаем результат от активити
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                isLogin = true;
                time_start.setToNow();
            }else {
                finish();
            }
        }
    }
}