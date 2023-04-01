package com.goally.assignment.data.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goally.assignment.data.preferences.CustomSharedPref
import com.goally.assignment.data.preferences.UserPreferences
import com.goally.assignment.domain.db.AppDataBase

open class BaseActivity : AppCompatActivity() {

    lateinit var userPreferences: UserPreferences
    lateinit var customPreferences: CustomSharedPref
    private var db = AppDataBase.getAppDB(App.context)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferences = UserPreferences(this)
        customPreferences = CustomSharedPref(this)

    }
}