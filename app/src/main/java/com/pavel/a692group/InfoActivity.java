package com.pavel.a692group;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.pavel.a692group.request.http.HttpGetRequestTask;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by p.mazhnik on 17.11.2017.
 * to 692group
 */

public class InfoActivity extends AppCompatActivity {
    final Context context = this;

    private ConstraintLayout ConstraintLayout;
    private Button SendButton;
    private EditText EditText;

    ArrayList<String> group_str;

    private ArrayList<CheckBox> group_list;
    private CheckBox CheckBox_All;

    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ConstraintLayout = findViewById(R.id.info_container);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db_init();
        CheckBoxCreate();


        EditText = findViewById(R.id.editText);
        EditText.setText(context.getString(R.string.hello_text));

        SendButton = findViewById(R.id.send_button);
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditText.getText() != null && !Objects.equals(EditText.getText().toString().trim(), "")) {
                    // открываем подключение
                    db = databaseHelper.open();
                    if(CheckBox_All.isChecked()) {
                        //получаем данные из бд в виде курсора
                        cursor = db.rawQuery("select * from " + DBHelper.TABLE, null);
                        SQL_request(cursor, EditText.getText().toString());
                        cursor.close();
                    } else {
                        for (int i = 0; i < group_list.size(); ++i) {
                            if (group_list.get(i).isChecked()) {
                                cursor = db.rawQuery("select * from " + DBHelper.TABLE + " where " + DBHelper.COLUMN_GROUP
                                        + "=?", new String[]{group_list.get(i).getText().toString()});
                                SQL_request(cursor, EditText.getText().toString());
                                cursor.close();
                            }
                        }
                    }
                    db.close();
                    EditText.getText().clear();
                } else {
                    Toast.makeText(context, context.getString(R.string.empty_text), Toast.LENGTH_SHORT).show();
                }
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
            Toast.makeText(context, context.getString(R.string.empty_db), Toast.LENGTH_SHORT).show();
        }
        String URL = new VkAPI.Messages.send(this)
                .user_ids(_ids) //id_vk
                .message(str)
                .build();
        HttpGetRequestTask request = new HttpGetRequestTask(URL, null);
        request.execute(); //выолнение запроса в отдельном потоке
    }

    private void db_init(){
        databaseHelper = new DBHelper(this);
        // инициализируем базу данных
        //TODO: добавить возможность редактирования бд
        db = databaseHelper.open();
        group_str = new ArrayList<>();
        cursor = db.rawQuery("select * from " + DBHelper.TABLE + " group by " + DBHelper.COLUMN_GROUP, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.COLUMN_GROUP);
            do{
                group_str.add(cursor.getString(idIndex));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private void CheckBoxCreate(){
        CheckBox_All = findViewById(R.id.checkBoxAll);
        CheckBox_All.setText(DBHelper.TABLE);

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
}