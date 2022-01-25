package com.kizadev.scanapps.storage

import androidx.datastore.preferences.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T, private val customKey: String? = null) :
    ReadWriteProperty<PrefStorage, T> {

    private var storedValue: T? = null

    override fun getValue(thisRef: PrefStorage, property: KProperty<*>): T {
        val key = createKey(customKey ?: property.name, defaultValue)
        if (storedValue == null) {
            thisRef.scope.launch {
                val flow = thisRef.dataStore.data.map { prefs ->
                    prefs[key] ?: defaultValue
                }
                storedValue = runBlocking(Dispatchers.IO) {
                    flow.first()
                }
            }
        }

        return storedValue!!
    }

    override fun setValue(thisRef: PrefStorage, property: KProperty<*>, value: T) {
        storedValue = value
        val key = createKey(customKey ?: property.name, defaultValue)
        thisRef.scope.launch {
            thisRef.dataStore.edit { settings ->
                settings[key] = defaultValue
            }
        }
    }

    private fun createKey(name: String, defaultValue: T): Preferences.Key<T> =
        when (defaultValue) {
            is Int -> intPreferencesKey(name)
            is Long -> longPreferencesKey(name)
            is Double -> doublePreferencesKey(name)
            is Float -> floatPreferencesKey(name)
            is String -> stringPreferencesKey(name)
            is Boolean -> booleanPreferencesKey(name)
            else -> error("This type can't be stored into preferences")
        }.run { this as Preferences.Key<T> }
}
