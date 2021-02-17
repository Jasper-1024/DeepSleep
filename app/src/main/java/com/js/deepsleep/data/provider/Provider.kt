package com.js.deepsleep.data.provider

enum class ProviderMethod(var value: String) {
    GetAppSt("GetAppSt"),
    GetExtends("GetExtends")
}

object PParameters {
    const val packageName: String = "packageName"
    const val appSt: String = "appSt"
    const val wakelock: String = "wakelock"
    const val alarm: String = "alarm"
}

