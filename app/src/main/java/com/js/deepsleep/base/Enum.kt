package com.js.deepsleep.base

enum class Type(value: String) {
    Wakelock("Wakelock"), Alarm("Alarm"), Service("Service")
}

enum class AppType(var type: Int) {
    All(0), User(1), System(2), Restricted(3)
}

enum class Sort(var type: Int) {
    Name(0), Count(1)
}