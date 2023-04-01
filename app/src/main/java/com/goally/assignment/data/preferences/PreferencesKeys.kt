package com.goally.assignment.data.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {

    val USER_PREFERENCES_NAME = "Yapiee"
    val email = stringPreferencesKey("email")
    val userModel = stringPreferencesKey("userModel")
    val firebaseToken = stringPreferencesKey("firebaseToken")
    val language = stringPreferencesKey("language")
    val userId = stringPreferencesKey("userId")
    val tokenCurrentUser = stringPreferencesKey("currentToken")

}