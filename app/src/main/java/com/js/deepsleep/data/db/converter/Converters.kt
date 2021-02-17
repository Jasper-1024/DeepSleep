package com.js.deepsleep.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.js.deepsleep.BasicApp

class Converters {
    @TypeConverter
    fun setToString(set: Set<String>): String {
        return BasicApp.gson.toJson(set)
    }

    @TypeConverter
    fun stringToSet(str: String): Set<String> {
        val listType = object : TypeToken<Set<String>>() {}.type
        return BasicApp.gson.fromJson(str, listType)
    }

}