package com.example.preferencesdatastore.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

class DataStoreManager(val context: Context) {

    val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "local")

    suspend fun saveString (key: String , value: String){
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value

        }
    }

    suspend fun getString(key: String): String? {
        return context.dataStore.data.map{
            it[stringPreferencesKey(key)]
        }.first()
    }
}