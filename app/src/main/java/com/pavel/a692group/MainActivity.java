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
 * Description: activity с единственной кнопкой, которая вызывает InfoActivity.
 * Точка входа в приложение, вызывает LoginActivity и инициализирует базу данных.
 */

//TODO: в последствии класс нужно удалить за ненадобностью.

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    //private Button new_year_button; //[v.1.0.1 new_year]
    private static boolean isLogin = false;

    DBHelper databaseHelper;
    private final Time time_start = new Time(Time.getCurrentTimezone());
    private Time time_current = new Time(Time.getCurrentTimezone());

    /*
    Первым идет метод OnCreate. В нем мы мы инициализируем наши поля и проводим первоначальную настройку activity,
    проверяя входные параметры или предыдущее состояние activity.
    Это предыдущее состояние приходит в качестве аргумента в виде объекта bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //System.out.println("Start class MainActivity");
        Toast.makeText(this, "call onCreate()", Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState);

        databaseHelper = new DBHelper(getApplicationContext());
        databaseHelper.create_db();

        //time_current.setToNow();
        /*String str = "" + (time_current.second - time_start.second);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();*/
    }

    /*
    После метода OnCreate activity приходит в состояние created. На данном этапе оно еще не видно для пользователя.
    Затем вызывается метод OnStart, в которой мы проводим настройку, касающуюся видимой части activity.
    Например в этом методе мы можем зарегистрировать BroadcastReceiver, если он связан с изменением интерфейса.
    После этого callback activity приходит состояние Started и становится видимым для пользователя,
    но еще не готовым для взаимодействия. Сразу же после метода OnStart вызывается callback-метод OnResume.
    В этом методе нам нужно настроить activity, чтобы оно было готово для взаимодействия с пользователем.
    В частности, мы навешиваем обработчики события на кнопки, на прокручивающиеся списки, запускаем анимации.
    После вызова этого метода activity приходится в состояние OnResume и остается в нем, пока пользователь работает с ним.
     */

    @Override
    protected void onStart() {
        Toast.makeText(this, "call onStart()", Toast.LENGTH_LONG).show();
        super.onStart();
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); //logo
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if(!isLogin) startLoginActivity(); // когда запускается LoginActivity, вызывается метод onPause()
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "call onResume()", Toast.LENGTH_LONG).show();
        super.onResume();
        loginButton = (Button) findViewById(R.id.login_button);
        if(isLogin) {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startInfoActivity();
                }
            });
        }

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

    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "call onPause()", Toast.LENGTH_LONG).show();
        super.onPause();
    }

    //onRestoreInstanceState() вызывается после onStart() и до onResume(); вызывается при наличии сохраненного состояния.
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Toast.makeText(this, "call onRestoreInstanceState()", Toast.LENGTH_LONG).show();
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void startInfoActivity() {
        startActivity(new Intent(this, InfoActivity.class));
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //получаем результат от активити, вызывается при закрытии LoginActivity
        Toast.makeText(this, "call onActivityResult()", Toast.LENGTH_LONG).show();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                isLogin = true;
                time_start.setToNow();
            } else {
                finish(); //закрываем MainActivity, если LoginActivity не вернул положительный результат
            }
        }
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "call onStop()", Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "call onDestroy()", Toast.LENGTH_LONG).show();
        super.onDestroy();
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
}