package com.teamx.hatlyDriver.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.teamx.hatlyDriver.MainApplication.Companion.context
import com.teamx.hatlyDriver.constants.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreProvider(context: Context) {

    //Create the dataStore
//    private val dataStore : DataStore<Preferences> = context.createDataStore(name = AppConstants.DataStore.DATA_STORE_NAME)

    //Create some keys
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConstants.DataStore.DATA_STORE_NAME)
        val IS_LOCALIZATION_KEY = booleanPreferencesKey(AppConstants.DataStore.LOCALIZATION_KEY_NAME)
        val USER_NAME_KEY = stringPreferencesKey(AppConstants.DataStore.USER_NAME_KEY)
        val TOKEN = stringPreferencesKey(AppConstants.DataStore.TOKEN)
        val USER_ID = stringPreferencesKey(AppConstants.DataStore.USER_ID)
        val DETAILS = stringPreferencesKey(AppConstants.DataStore.DETAILS)
        val SAVE_ID = stringPreferencesKey(AppConstants.DataStore.SAVE_ID)
        val AVATAR = stringPreferencesKey(AppConstants.DataStore.AVATAR)
        val NAME = stringPreferencesKey(AppConstants.DataStore.NAME)
        val PAYTYPE = stringPreferencesKey(AppConstants.DataStore.PAYTYPE)
        val NUMBER = stringPreferencesKey(AppConstants.DataStore.NUMBER)

    }

    //Store data
    suspend fun storeData(isLocalizationKey: Boolean, name: String,token: String, details:String) {
        context.dataStore.edit {
            it[IS_LOCALIZATION_KEY] = isLocalizationKey
            it[USER_NAME_KEY] = name
            it[TOKEN] = token
            it[DETAILS] = details
        }

    }

    //get Token by using this
    val token : Flow<String?> =  context.dataStore.data.map {
        it[TOKEN]
    }



    val details : Flow<String?> =  context.dataStore.data.map {
        it[DETAILS]
    }

    val number: Flow<String?> = context.dataStore.data.map {
        it[NUMBER]
    }
    val avatar: Flow<String?> = context.dataStore.data.map {
        it[AVATAR]
    }

    val name: Flow<String?> = context.dataStore.data.map {
        it[NAME]
    }

    //save token by using this functionn
    suspend fun saveUserToken(token: String){
        context.dataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun saveUserDetails(firstname: String,email: String){
        context.dataStore.edit {
            it[DETAILS] = firstname
            it[DETAILS] = email
        }
    }

    suspend fun saveUserDetails(firstname: String, email: String,number:String) {
        context.dataStore.edit {
            it[NAME] = firstname
            it[DETAILS] = email
            it[NUMBER] = number
        }
    }

    val user_id : Flow<String?> =  context.dataStore.data.map {
        it[USER_ID]
    }

    suspend fun saveUserID(user_id: String) {
        context.dataStore.edit {
            it[USER_ID] = user_id
        }
    }

    suspend fun saveUserDetails(firstname: String, email: String, avatar: String,number:String) {
        context.dataStore.edit {
            it[NAME] = firstname
            it[DETAILS] = email
            it[AVATAR] = avatar
            it[NUMBER] = number
        }
    }

    suspend fun removeAll() {
        context.dataStore.edit {
            it.remove(TOKEN)
            it.remove(DETAILS)
            it.remove(AVATAR)
            it.remove(NAME)
            it.remove(PAYTYPE)
            it.remove((USER_ID))
            it.remove((NUMBER))
        }

    }

    val saveId: Flow<String?> = context.dataStore.data.map {
        it[SAVE_ID]
    }


    //Create an Localization flow
    val localizationFlow: Flow<Boolean> = context.dataStore.data.map {
        it[IS_LOCALIZATION_KEY] ?: false
    }

}