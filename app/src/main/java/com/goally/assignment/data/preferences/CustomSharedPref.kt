package com.goally.assignment.data.preferences

import android.content.Context
import android.content.SharedPreferences

class CustomSharedPref(context: Context) {
    private val PREFS_NAME = "UserSave"
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPref.edit()

    fun save(KEY_NAME: String, value: Int) {

        editor.putInt(KEY_NAME, value)

        editor.commit()
    }

    fun saveString(KEY_NAME: String, value: String) {

        editor.putString(KEY_NAME, value)

        editor.commit()
    }

    fun saveLogin(KEY_NAME: String, value: Boolean) {

        editor.putBoolean(KEY_NAME, value)

        editor.commit()
    }

    fun isLogin(KEY_NAME: String): Boolean {

        return sharedPref.getBoolean(KEY_NAME, false)
    }

    fun getValueInt(KEY_NAME: String): Int {

        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueString(KEY_NAME: String): String? {

        return sharedPref.getString(KEY_NAME, "You")
    }


    fun clearSharedPreference() {

        editor.clear()
        editor.commit()
    }
}