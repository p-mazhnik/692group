package com.pavel.a692group;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.pavel.a692group.request.http.HttpGetRequestTask;
import com.pavel.a692group.room.AppDatabase;
import com.pavel.a692group.room.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by p.mazhnik on 17.11.2017.
 * to 692group
 */

public class InfoActivity extends AppCompatActivity {
    final Context context = this;

    private ConstraintLayout ConstraintLayout;
    private Button SendButton;
    private Button EditDbButton;
    private EditText EditText;

    private Handler mHandler;

    private AppDatabase mDatabase;
    private Disposable mDisposable;

    ArrayList<String> group_str;

    private ArrayList<CheckBox> group_list;
    private CheckBox CheckBox_All;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ConstraintLayout = findViewById(R.id.info_container);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mDatabase = AppDatabase.createPersistentDatabase(this);

        group_str = new ArrayList<>();

        mDisposable = mDatabase
                .getUserDao()
                .getAllGroup()
                //.observeOn(AndroidSchedulers.mainThread()) //TODO: Чтобы результат пришел в UI поток, используем observeOn. Избавиться от messages ?
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> groaps) throws Exception {
                        // Log.d("Inf", "size: " + groaps.get(0).mGroup);
                        Message message = mHandler.obtainMessage(3, groaps); //Отправляем данные в UI поток
                        message.sendToTarget();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Inf", "accept: ", throwable);
                    }
                });
        //TODO: добавить возможность редактирования бд

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                if(message.what == 3) {
                    group_str.addAll((List<String>) message.obj);
                    CheckBoxCreate(); //cоздаем чекбоксы на основе списка групп
                }
            }
        };

        EditText = findViewById(R.id.editText);
        EditText.setText(context.getString(R.string.hello_text));

        SendButton = findViewById(R.id.send_button); //TODO: обработать ошибку отправки при невыборе списка
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //TODO: добавить вызов окошка с предупреждением об отправке
            }
        });

        EditDbButton = findViewById(R.id.info_edit_button);
        EditDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditDbActivity();
            }
        });
    }

    /*private void SQL_request(Cursor cursor, String str){
        ArrayList<Integer> _ids = new ArrayList<>();
        if (cursor.moveToFirst()) {
            //System.out.println("ONE ROW");
            int idIndex = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                        *//*System.out.println(idIndex);
                        System.out.println(cursor.getInt(idIndex));*//*
            do{
                //System.out.println(cursor.getInt(idIndex));
                _ids.add(cursor.getInt(idIndex));
            } while (cursor.moveToNext());
        } else{
            //System.out.println("NON ROW");
            Toast.makeText(context, context.getString(R.string.empty_db), Toast.LENGTH_SHORT).show();
        }
        String URL = new VkAPI.Messages.send(this)
                .user_ids(_ids) //id_vk
                .message(str)
                .build();
        HttpGetRequestTask request = new HttpGetRequestTask(URL, null);
        request.execute(); //выолнение запроса в отдельном потоке
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void CheckBoxCreate(){
        CheckBox_All = findViewById(R.id.checkBoxAll);
        CheckBox_All.setText(User.TABLE);

        //layout params for every CheckBox
        ConstraintLayout.LayoutParams AllCheckBoxParams = (android.support.constraint.ConstraintLayout.LayoutParams)
                CheckBox_All.getLayoutParams();

        if(!group_str.isEmpty()){ //открытие бд, получение списка групп и создание в активити чекбоксов с этими группами.
            int index = 0;
            int id = R.id.checkBoxAll;
            group_list = new ArrayList<>();
            while(index != group_str.size()){
                CheckBox Box = new CheckBox(this);
                Box.setText(group_str.get(index));
                Box.setId(index + 10);

                ConstraintLayout.LayoutParams Params = new ConstraintLayout.LayoutParams(
                        android.support.constraint.ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        android.support.constraint.ConstraintLayout.LayoutParams.WRAP_CONTENT);
                Params.leftMargin = AllCheckBoxParams.leftMargin;
                Params.topMargin = AllCheckBoxParams.topMargin;
                Params.startToStart = AllCheckBoxParams.startToStart;
                Params.topToTop = AllCheckBoxParams.topToTop;
                Params.verticalBias = AllCheckBoxParams.verticalBias;
                Params.bottomToTop = id;
                id = Box.getId();

                ConstraintLayout.addView(Box, Params);

                group_list.add(Box);
                index++;
            }
        }

        CheckBox_All.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for(int i = 0; i < group_list.size(); ++i){
                        group_list.get(i).setChecked(true);
                    }
                } else {
                    for(int i = 0; i < group_list.size(); ++i){
                        group_list.get(i).setChecked(false);
                    }
                }
            }
        });
    }

    private void startEditDbActivity(){
        startActivity(new Intent(this, EditDbActivity.class));
    }
}