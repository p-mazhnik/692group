package com.pavel.a692group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.Time
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by p.mazhnik on 17.11.2017.
 * to 692group
 * Description: activity с единственной кнопкой, которая вызывает InfoActivity.
 * Точка входа в приложение, вызывает LoginFragment и инициализирует базу данных.
 */
//TODO: в последствии класс нужно удалить за ненадобностью.
class MainActivity : AppCompatActivity() {
    private var mLoginButton: Button? = null

    private val time_start =
        Time(Time.getCurrentTimezone())
    private val time_current =
        Time(Time.getCurrentTimezone())

    /*
    Первым идет метод OnCreate. В нем мы мы инициализируем наши поля и проводим первоначальную настройку activity,
    проверяя входные параметры или предыдущее состояние activity.
    Это предыдущее состояние приходит в качестве аргумента в виде объекта bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //System.out.println("Start class MainActivity");
        //Toast.makeText(this, "call onCreate()", Toast.LENGTH_LONG).show();
        super.onCreate(savedInstanceState)

        /*databaseHelper = new DBHelper(getApplicationContext());
        databaseHelper.create_db();*/

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
    override fun onStart() {
        //Toast.makeText(this, "call onStart()", Toast.LENGTH_LONG).show();
        super.onStart()
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar //logo
        actionBar!!.setLogo(R.mipmap.ic_launcher)
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)
        if (!isLogin) startLoginActivity() // когда запускается LoginFragment, вызывается метод onPause()
    }

    override fun onResume() {
        //Toast.makeText(this, "call onResume()", Toast.LENGTH_LONG).show();
        super.onResume()
        mLoginButton = findViewById<View>(R.id.login_button) as Button
        if (isLogin) {
            mLoginButton!!.setOnClickListener { startInfoActivity() }
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

    private fun startInfoActivity() {
        startActivity(Intent(this, `InfoActivity.java`::class.java))
    }

    private fun startLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        //получаем результат от AuthActivity, вызывается при закрытии LoginFragment или
        //Toast.makeText(this, "call onActivityResult()", Toast.LENGTH_LONG).show();
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                isLogin = true
                time_start.setToNow()
            } else {
                finish() //закрываем MainActivity, если Fragment не вернул положительный результат
            }
        }
    }

    companion object {
        private var isLogin = true //TODO
    }
}

