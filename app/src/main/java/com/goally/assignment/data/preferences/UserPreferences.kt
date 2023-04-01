package com.goally.assignment.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.goally.assignment.data.preferences.PreferencesKeys.USER_PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class UserPreferences(private val context: Context) {

    suspend fun saveStringValuesInPreference(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { pref ->
            pref[key] = value
        }
    }

    suspend fun saveBooleanValuesInPreference(key: Preferences.Key<Boolean>, value: Boolean) {
        context.dataStore.edit { pref ->
            pref[key] = value
        }
    }

    suspend fun saveIntValuesInPreference(key: Preferences.Key<Int>, value: Int) {
        context.dataStore.edit { pref ->
            pref[key] = value
        }
    }


    suspend fun saveUserModel(key: Preferences.Key<String>, userInfo: String) {
        context.dataStore.edit { pref ->
            pref[key] = userInfo
        }
    }

    suspend fun getUserModel(key: Preferences.Key<String>) =
        context.dataStore.data.map { pref ->
            pref[key] ?: ""
        }

    val getuserModel: Flow<String> = context.dataStore.data.map {
        it[PreferencesKeys.userModel] ?: ""
    }


    suspend fun getStringValuesFromPreference(key: Preferences.Key<String>) =
        context.dataStore.data.map { pref ->
            pref[key] ?: ""
        }

    suspend fun getBooleanValuesFromPreference(key: Preferences.Key<Boolean>) =
        context.dataStore.data.map { pref ->
            pref[key] ?: false
        }

    suspend fun getIntValuesFromPreference(key: Preferences.Key<Int>) =
        context.dataStore.data.map { pref ->
            pref[key] ?: 0
        }

    suspend fun removePref() {
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun removePrefItem(key: Preferences.Key<String>) {
        context.dataStore.edit {
            it.remove(key)
        }
    }
}