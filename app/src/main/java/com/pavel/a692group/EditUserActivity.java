package com.pavel.a692group;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pavel.a692group.data.datasource.room.AppDatabase;
import com.pavel.a692group.room.entity.User;

import java.util.List;

/**
 * Created by p.mazhnik on 01.01.2018.
 * to 692group
 */

public class EditUserActivity extends AppCompatActivity {
    public static String ID_KEY = "ID_KEY";
    public static String POS_KEY = "POS_KEY";

    private long mUserId;
    private User mUser;

    private EditText mIdTextEdit;
    private EditText mNameTextEdit;
    private Button mOkButton;

    private Handler mHandler;
    private Intent mIntentAnswer;

    private AppDatabase mDatabase;
    long taskCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mOkButton = (Button) findViewById(R.id.edit_user_ok_button);
        mIdTextEdit = findViewById(R.id.edit_id);
        mNameTextEdit = findViewById(R.id.edit_name);

        Intent intent = getIntent();
        mUserId = intent.getExtras().getLong(ID_KEY);

        getUserFromDb();

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDataBase(); //update db
                finish();
            }
        });

    }

    private void getUserFromDb(){
        /*mDisposable = mDatabase.userDao
                .getById(mUserId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        *//*
                        Если запись есть в базе, то она придет в accept сразу же после подписки.
                        И при каждом последующем обновлении этой записи в базе данных, она также будет приходить в accept.
                        Если записи нет, то сразу после подписки ничего не придет. А вот если она позже появится, то она придет в accept.
                        В данном случае запись придет всегда, но лист может быть пустым.
                         *//*
                        setEditText(users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Inf", "accept: ", throwable);
                    }
                });*/
    }

    private void setEditText(List<User> users){
        int size = users.size();
        if(size == 0){
            if(isNewUser()) { //размер 0, если id = 0
                mUser = new User();
            } else { //если такой id не нашелся в бд (что странно, т.к. мы выбираем из существующих)
                wrongFinish();
            }
        } else if (size == 1) {//если одна запись (а мы считаем, что id уникальные)
            mUser = users.get(0);
            mIdTextEdit.setText(mUser.getIdToString());
            mIdTextEdit.setInputType(0);
            mNameTextEdit.setText(mUser.getName());
        }
        else { //несколько записей тоже не может быть, id уникален
            wrongFinish();
        }
    }

    private void UpdateDataBase(){
        mUser.setName(mNameTextEdit.getText().toString());
        if (isNewUser()) {
            try {
                mUser.setIdFromString(mIdTextEdit.getText().toString());
            } catch (NumberFormatException nfe){
                wrongFinish(); //Если вдруг в поле id оказалось что-то кроме цифр.
            }
            mUser.setGroup(getString(R.string.group_new_user));
        }
        try {
            db_insert(mUser);
        } catch (Exception e){
            finish();
        }
        setResult(RESULT_OK);
    }

    private void db_insert(final User user){
        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.userDao.insert(user);
            }
        });
        t.start();*/
    }

    private boolean isNewUser(){
        return (mUserId == 0);
    }

    private void wrongFinish(){
        Toast.makeText(this, getString(R.string.nonexistent_user_id), Toast.LENGTH_LONG).show();
        finish();
    }
}
