@file:Suppress("RemoveExplicitTypeArguments")

package com.js.deepsleep

import com.js.deepsleep.base.Type
import com.js.deepsleep.data.db.AppDatabase
import com.js.deepsleep.data.repository.app.AppAR
import com.js.deepsleep.data.repository.app.AppRepo
import com.js.deepsleep.data.repository.backup.BackupRepository
import com.js.deepsleep.data.repository.extend.ER
import com.js.deepsleep.data.repository.extend.ExtendRepo
import com.js.deepsleep.data.repository.syncsp.SyncSp
import com.js.deepsleep.data.repository.syncsp.SyncSpRepo
import com.js.deepsleep.ui.about.AboutViewModel
import com.js.deepsleep.ui.app.AppViewModel
import com.js.deepsleep.ui.app.SyncStViewModel
import com.js.deepsleep.ui.extend.fbase.FBaseViewModel
import com.js.deepsleep.ui.extend.fbase.SyncExViewModel
import com.js.deepsleep.ui.help.HelpViewModel
import com.js.deepsleep.ui.mainactivity.MainViewModel
import com.js.deepsleep.ui.settings.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

var repository = module {

    /** AppRepo */
    single<AppRepo>(named("AppR")) {
        AppAR(
            AppDatabase.getInstance(BasicApp.context).appDao(),
            AppDatabase.getInstance(BasicApp.context).appInfoDao(),
            AppDatabase.getInstance(BasicApp.context).appStDao()
        )
    }

    /** ExtendRepo */
    single<ExtendRepo>(named("wakelockER")) {
        ER(
            AppDatabase.getInstance(BasicApp.context).extendDao(), Type.Wakelock
        )
    }

    /** ExtendRepo */
    single<ExtendRepo>(named("alarmR")) {
        ER(
            AppDatabase.getInstance(BasicApp.context).extendDao(), Type.Alarm
        )
    }

    /** BackupRepository */
    single<BackupRepository>(named("backupR")) {
        BackupRepository(
            AppDatabase.getInstance(BasicApp.context).backupDao()
        )
    }

    /** SyncSpRepo */
    single<SyncSpRepo>(named("syncSpR")) {
        SyncSp(
            AppDatabase.getInstance(BasicApp.context).appStDao(),
            AppDatabase.getInstance(BasicApp.context).extendDao()
        )
    }

}

var viewModel = module {

    viewModel(named("AppVm")) {
        AppViewModel()
    }

    viewModel(named("SyncStVm")) {
        SyncStViewModel()
    }

    viewModel(named("SyncExVm")) {
        SyncExViewModel()
    }
    // MainViewModel
    viewModel(named("MainVm")) {
        MainViewModel()
    }

    viewModel(named("ExtendVm")) { (packageName: String, type: Type) ->
        FBaseViewModel(
            packageName,
            when (type) {
                Type.Wakelock -> get(named("wakelockER"))
                Type.Alarm -> get(named("alarmR"))
                else -> get(named("wakelockER"))
            }
        )
    }

    viewModel(named("HelpVm")) {
        HelpViewModel()
    }

    viewModel(named("AboutVm")) {
        AboutViewModel()
    }

    viewModel(named("SettingVm")) {
        SettingViewModel()
    }
}