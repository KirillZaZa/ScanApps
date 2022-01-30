package com.kizadev.scanapps.storage

import androidx.datastore.preferences.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T, private val customKey: String? = null) {

    operator fun provideDelegate(
        thisRef: PrefStorage,
        prop: KProperty<*>
    ): ReadWriteProperty<PrefStorage, T> {

        val key = createKey(customKey ?: prop.name, defaultValue)
        return object : ReadWriteProperty<PrefStorage, T> {

            private var storedValue: T? = null

            override fun setValue(thisRef: PrefStorage, property: KProperty<*>, value: T) {
                storedValue = value

                thisRef.scope.launch {
                    thisRef.dataStore.edit { prefs ->
                        prefs[key] = value
                    }
                }
            }

            override fun getValue(thisRef: PrefStorage, property: KProperty<*>): T {
                if (storedValue == null) {
                    val flowValue = thisRef.dataStore.data
                        .map { prefs ->
                            prefs[key] ?: defaultValue
                        }
                    storedValue = runBlocking(Dispatchers.IO) {
                        flowValue.first()
                    }
                }

                return storedValue!!
            }
        }
    }

    @Suppress("UNCHECKED CAST")
    fun createKey(name: String, value: T): Preferences.Key<T> =
        when (value) {
            is Int -> intPreferencesKey(name)
            is Long -> longPreferencesKey(name)
            is Double -> doublePreferencesKey(name)
            is Float -> floatPreferencesKey(name)
            is String -> stringPreferencesKey(name)
            is Boolean -> booleanPreferencesKey(name)
            else -> error("This type can't be stored into preferences")
        }.run { this as Preferences.Key<T> }
}
