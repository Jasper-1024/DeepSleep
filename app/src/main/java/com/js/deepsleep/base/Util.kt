package com.js.deepsleep.base

import java.util.*

// filter list
inline fun <T : Any> List<T>.appType(status: (T) -> Boolean): List<T> {
    return this.filter { status(it) }
}

// search list
inline fun <T : Any> List<T>.search(query: String, text: (T) -> String): List<T> {
    /*lowerCase and no " " */
    val q = query.toLowerCase(Locale.ROOT).trim { it <= ' ' }
    if (q == "") {
        return this
    }
    return this.filter {
        text(it).toLowerCase(Locale.ROOT).contains(q)
    }
}

// sort list
fun <T : Any> List<T>.sort(comparator: Comparator<in T>): List<T> {
    return this.sortedWith(comparator)
}