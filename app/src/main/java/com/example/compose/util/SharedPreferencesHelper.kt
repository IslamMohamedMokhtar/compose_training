package com.example.compose.util

import android.content.Context
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    var token: String?
        get() = get("token", "")
        set(value) = put("token", value)
    private inline fun <reified T> get(key: String, defaultValue: T): T {
        return when (T::class) {
            String::class -> sharedPreferences.getString(key, defaultValue as String) as T
            Int::class -> sharedPreferences.getInt(key, defaultValue as Int) as T
            Boolean::class -> sharedPreferences.getBoolean(key, defaultValue as Boolean) as T
            Float::class -> sharedPreferences.getFloat(key, defaultValue as Float) as T
            Long::class -> sharedPreferences.getLong(key, defaultValue as Long) as T
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    private inline fun <reified T> put(key: String, value: T?) {
        when(value){
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IllegalArgumentException("Unsupported data type")
        }
        editor.apply()
    }
}