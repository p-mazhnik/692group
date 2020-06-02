package com.pavel.a692group.presentation.login

import androidx.fragment.app.Fragment

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */
class AuthActivity : AuthSingleFragmentActivity() {
    override fun getFragment(): Fragment {
        return LoginFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        finish()
    }
}

