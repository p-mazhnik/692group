package com.pavel.a692group;

import androidx.fragment.app.Fragment;

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */
public class AuthActivity extends AuthSingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return LoginFragment.newInstance();
    }
}
