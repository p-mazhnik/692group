package com.pavel.a692group.presentation.login;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import com.pavel.a692group.R;

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */
public abstract class AuthSingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_single_fragment);
        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.auth_fragment_container, getFragment())
                    .commit();
        }
    }

    protected abstract Fragment getFragment();
}
