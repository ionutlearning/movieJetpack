package com.example.moviejetpack.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPrefsHelper(context: Context) {
    private var prefs: SharedPreferences? = null

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    companion object {
        private const val PREF_TIME = "pref_time"
    }

    fun saveUpdateTime(time: Long) {
        prefs?.edit(commit = true) {
            putLong(PREF_TIME, time)
        }
    }

    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0)
}