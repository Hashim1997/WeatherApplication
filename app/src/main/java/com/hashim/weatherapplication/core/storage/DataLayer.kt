package com.hashim.weatherapplication.core.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.hashim.weatherapplication.core.constant.DataStoreKeys
import com.hashim.weatherapplication.core.utils.extension.toObject
import com.hashim.weatherapplication.core.utils.extension.toJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.java
import kotlin.jvm.javaObjectType

@Singleton
@Suppress("UNCHECKED_CAST")
class DataLayer @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreKeys.DATASTORE_NAME)

    suspend fun <T> save(key: String, value: T) {
        var baseValue = value

        context.dataStore.edit {
            when (value) {
                is String -> it[stringPreferencesKey(key)] = baseValue as String
                is Boolean -> it[booleanPreferencesKey(key)] = baseValue as Boolean
                is Float -> it[floatPreferencesKey(key)] = baseValue as Float
                is Double -> it[doublePreferencesKey(key)] = baseValue as Double
                is Int -> it[intPreferencesKey(key)] = baseValue as Int
                is Long -> it[longPreferencesKey(key)] = baseValue as Long
                is Set<*> -> it[stringSetPreferencesKey(key)] = baseValue as Set<String>
                else -> it[stringPreferencesKey(key)] = baseValue.toJson()
            }
        }
    }

    // reified is a way to access T type, you can't do that in normal generics
    suspend inline fun <reified T> get(key: String, isSecure: Boolean = false): T? {
        return get(key, T::class.java, isSecure)
    }

    /**
     * private modifier is not applicable due to @throws Inline function cannot access non-public-API: @PublishedApi vs @Suppress vs @JvmSynthetic
     * @PublishedApi internal is the intended way of exposing non-public API for use in public inline functions.
     */
    @PublishedApi
    internal suspend fun <T> get(key: String, `class`: Class<T>, isSecure: Boolean = false): T? {
        val dataStoreKey = when (`class`) {
            String::class.java -> stringPreferencesKey(key)
            Boolean::class.javaObjectType -> booleanPreferencesKey(key)
            Float::class.java -> floatPreferencesKey(key)
            Double::class.java -> doublePreferencesKey(key)
            Int::class.javaObjectType -> intPreferencesKey(key)
            Long::class.java -> longPreferencesKey(key)
            Set::class.java -> stringSetPreferencesKey(key)
            else -> return get(key, String::class.java, isSecure)?.toObject(`class`)
        }

        val preferences = context.dataStore.data.first()
        val savedData = preferences[dataStoreKey]

        return savedData as T?
    }

    // Call this function wisely
    suspend fun clearData() {
        context.dataStore.edit {
            it.clear()
        }
    }
}