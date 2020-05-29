package com.pavel.a692group.data.util

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class SharedPreferencesLiveData (
    private val sharedPreferences: SharedPreferences,
    private val key: String
): LiveData<String>() {

    private val mTokenSharedPreferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences?, _key: String? ->
            if (_key == key) {
                value = sharedPreferences?.getString(key, "")
            }
        }


    override fun onActive() {
        super.onActive()
        value = sharedPreferences.getString(key, "")
        sharedPreferences.registerOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(mTokenSharedPreferenceListener)
    }
}

