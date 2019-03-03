package com.pavel.a692group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.pavel.a692group.room.AppDatabase;
import com.pavel.a692group.room.entity.User;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by p.mazhnik on 31.12.2017. 23:50
 * to 692group
 */

public class EditDbActivity extends AppCompatActivity {
    private GridView mGridView;
    private FloatingActionButton mNewButton;

    private Handler mHandler;

    private AppDatabase mDatabase;
    private Disposable mDisposable;

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

        mDatabase = AppDatabase.createPersistentDatabase(this);

        mDisposable = mDatabase
                .getUserDao()
                .getAll()
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        Message message = mHandler.obtainMessage(1, users); //Отправляем данные в UI поток
                        message.sendToTarget();
                        //Toast.makeText(EditDbActivity.this, "sendMessage", Toast.LENGTH_LONG).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Inf", "accept: ", throwable);
                    }
                });

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                if(message.what == 1) {
                    setGridView((List<User>) message.obj);
                    //Toast.makeText(EditDbActivity.this, "message", Toast.LENGTH_LONG).show();
                }
            }
        };

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditUserActivity(0);
            }
        });
    }

    private void setGridView(List<User> users) {
        if(users.size() == 0) {
            Toast.makeText(this, R.string.nonexistent_user_id, Toast.LENGTH_LONG).show();
            finish();
        }
        /*
        Для того, чтобы мы могли заполнить наш GridView созданым объектом, надо задать адаптер.
        */
        mGridView.setAdapter(new UserAdapter(this, users));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                startEditUserActivity(id);
            }
        });
    }

    private void startEditUserActivity(long id){
        Intent i = new Intent(EditDbActivity.this, EditUserActivity.class);
        i.putExtra(EditUserActivity.ID_KEY, id); //здесь id это реальный id из бд
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //TODO: обновить видимые значения в GridView
        }
    }

    @Override
    protected void onDestroy() {
        // Toast.makeText(this, "Call EditDb.onDestroy" + mDisposable.isDisposed(), Toast.LENGTH_LONG).show();
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
