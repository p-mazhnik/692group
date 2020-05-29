package com.pavel.a692group;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.pavel.a692group.data.datasource.room.AppDatabase;
import com.pavel.a692group.room.entity.User;

import java.util.List;

/**
 * Created by p.mazhnik on 31.12.2017. 23:50
 * to 692group
 */

public class EditDbActivity extends AppCompatActivity {
    private GridView mGridView;
    private FloatingActionButton mNewButton;
    private Button mButton;

    private UserAdapter mUserAdapter;
    private Intent mIntent;

    private AppDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_db);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mGridView = (GridView) findViewById(R.id.edit_db_grid_view);
        mGridView.setColumnWidth(60);
        mGridView.setVerticalSpacing(20);
        mGridView.setHorizontalSpacing(20);
        mNewButton = (FloatingActionButton) findViewById(R.id.edit_db_floatingActionButton);
        mButton = findViewById(R.id.edit_db_button);

        /*
        Для того, чтобы мы могли заполнить наш GridView созданым объектом, надо задать адаптер.
        */
        mUserAdapter = new UserAdapter(this);
        mGridView.setAdapter(mUserAdapter);

        readFromDb();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserAdapter.notifyDataSetChanged();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                startEditUserActivity(id, position);
            }
        });

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditUserActivity(0, -1);
            }
        });
    }

    private void readFromDb(){
        /*mDisposable = mDatabase.userDao
                .getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception { //Это UI поток, т.к. использовали observeOn
                        if(users.size() == 0) {
                            Toast.makeText(EditDbActivity.this, R.string.nonexistent_user_id, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        mUserAdapter.setData(users);
                        mUserAdapter.notifyDataSetChanged();
                        // Toast.makeText(EditDbActivity.this, "readFromDb", Toast.LENGTH_LONG).show();
                        Log.d("Inf", "message");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("Inf", "accept: ", throwable);
                    }
                });*/
    }

    private void startEditUserActivity(long id, int position){
        mIntent = new Intent(EditDbActivity.this, EditUserActivity.class);
        mIntent.putExtra(EditUserActivity.ID_KEY, id); //здесь id это реальный id из бд
        mIntent.putExtra(EditUserActivity.POS_KEY, position);
        startActivityForResult(mIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            readFromDb();
        }
    }
}
