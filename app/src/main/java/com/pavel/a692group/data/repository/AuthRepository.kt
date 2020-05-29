package com.pavel.a692group.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.pavel.a692group.data.util.SharedPreferencesLiveData

class AuthRepository (
    private val mSharedPreferences: SharedPreferences
) {
    suspend fun setCurrentUsername(username: String) {
        mSharedPreferences.edit{
            putString(PREF_KEY_CURRENT_USERNAME, username)
        }
    }

    fun getCurrentUsername(): SharedPreferencesLiveData {
        return SharedPreferencesLiveData(mSharedPreferences, PREF_KEY_CURRENT_USERNAME)
    }

    suspend fun logout() {
        mSharedPreferences.edit{
            putString(PREF_KEY_CURRENT_USERNAME, "")
        }
    }

    companion object {
        private const val PREF_KEY_CURRENT_USERNAME = "PREF_KEY_CURRENT_USERNAME"
    }
}

