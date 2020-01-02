package com.pg.personalaccounting.core.bases

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import com.pg.personalaccounting.R

class BaseApplication : Application() {
    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var preferencesEditor: SharedPreferences.Editor

    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        preferences = provideSharedPreferences()
        preferencesEditor = preferences.edit()
    }

    private fun provideSharedPreferences(): SharedPreferences {
        return getSharedPreferences(getString(R.string.pref_name), 0)
    }

}