package com.js.deepsleep.tools

import android.view.Menu
import androidx.preference.PreferenceManager
import com.js.deepsleep.BasicApp
import java.util.*

// filter list
inline fun <T : Any> List<T>.appType(status: (T) -> Boolean): List<T> {
    return this.filter { status(it) }
}

// search list
inline fun <T : Any> List<T>.search(query: String, text: (T) -> String): List<T> {
    /*lowerCase and no " " */
    val q = query.lowercase(Locale.ROOT).trim { it <= ' ' }
    if (q == "") {
        return this
    }
    return this.filter {
        text(it).lowercase(Locale.ROOT).contains(q)
    }
}

// sort list
fun <T : Any> List<T>.sort(comparator: Comparator<in T>): List<T> {
    return this.sortedWith(comparator)
}

// 设置 menu 不可见
fun menuGone(menu: Menu, set: Set<Int>) {
    set.forEach {
        val filterUser = menu.findItem(it)
        filterUser.isVisible = false
    }
}

// 获取setting 状态

fun getSetting(key: String): Boolean {
    val sharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(BasicApp.context)
    return sharedPreferences.getBoolean(key, false)
}