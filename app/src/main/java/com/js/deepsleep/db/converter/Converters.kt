package com.js.deepsleep.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun setToString(set: Set<String>): String {
        return Gson().toJson(set)
    }

    @TypeConverter
    fun stringToSet(str: String): Set<String> {
        val listType = object : TypeToken<Set<String>>() {}.type
        return Gson().fromJson(str, listType)
    }

}