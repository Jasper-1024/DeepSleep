package com.js.deepsleep.data.db.data

import com.js.deepsleep.data.db.entity.AppSt
import com.js.deepsleep.data.db.entity.Extend

class TestData {
    companion object {
        const val packageName = "test1"
        const val wakelock = "wakelock"
        const val alarm = "alarm"
        const val server = "server"

        val appSt = AppSt(packageName = packageName, wakelock = true, alarm = true, service = true)

        val appSts: List<AppSt> = listOf(
            AppSt(packageName = packageName, wakelock = false, alarm = false, service = false),
            AppSt(packageName = "test2", wakelock = true, alarm = true, service = true),
            AppSt(packageName = "test3", wakelock = true, alarm = true, service = true)
        )

        val extend = Extend(
            packageName,
            wakelock,
            setOf("wakelock_allow1", "wakelock_allow2", "wakelock_allow3", "wakelock_allow4"),
            setOf("wakelock_block1", "wakelock_block2", "wakelock_block3"),
            setOf("wakelock_re1", "wakelock_re2", "wakelock_re3")
        )

        val extends: List<Extend> = listOf(
            Extend(
                packageName,
                wakelock,
                setOf("wakelock_allow1", "wakelock_allow2", "wakelock_allow3"),
                setOf("wakelock_block1", "wakelock_block2", "wakelock_block3"),
                setOf("wakelock_re1", "wakelock_re2", "wakelock_re3")
            ),
            Extend(
                packageName,
                alarm,
                setOf("wakelock_allow1", "wakelock_allow1", "wakelock_allow1"),
                setOf("wakelock_block1", "wakelock_block2", "wakelock_block2"),
                setOf("wakelock_re1", "wakelock_re2", "wakelock_re3")
            ),
            Extend(packageName, server, setOf(), setOf(), setOf())
        )

    }
}