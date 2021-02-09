package com.js.deepsleep

import com.js.deepsleep.data.db.AppDatabase
import com.js.deepsleep.data.repository.app.AppAR
import com.js.deepsleep.data.repository.app.AppRepo
import com.js.deepsleep.ui.app.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
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

}

@OptIn(KoinApiExtension::class)
var viewModel = module {

    viewModel(named("AppVm")) {
        AppViewModel()
    }

    /** AppInfoRepository */

    /** AppInfoRepository */
//    single<AppInfoRepository>(named("APR")) {
//        IAppInfoRepository(
//            AppDatabase.getInstance(BasicApp.context).appInfoDao(),
//            AppDatabase.getInstance(BasicApp.context).backupDao()
//        )
//    }

    /** ServiceRepository*/

    /** ServiceRepository*/
//    single<FRepository>(named("SR")) {
//        IServiceR(
//            AppDatabase.getInstance(BasicApp.context).serviceDao()
//        )
//    }

    /** WakeLockRepository */

    /** WakeLockRepository */
//    single<FRepository>(named("WR")) {
//        IWakelockR(
//            AppDatabase.getInstance(BasicApp.context).wakeLockDao()
//        )
//    }
//
//    single<InfoRepository>(named("IRA")) {
//        IAlarmIR(AppDatabase.getInstance(BasicApp.context).infoDao())
//    }
//
//    single<InfoRepository>(named("IRS")) {
//        IServiceIR(AppDatabase.getInstance(BasicApp.context).infoDao())
//    }
//
//    single<InfoRepository>(named("IRW")) {
//        IWakelockIR(AppDatabase.getInstance(BasicApp.context).infoDao())
//    }

    /** BackupRepository */

    /** BackupRepository */
//    single<BackupRepository>(named("BackupR")) {
//        BackupRepository(
//            AppDatabase.getInstance(BasicApp.context).backupDao()
//        )
//    }


    /**alarm*/


    /**alarm*/
//    viewModel(named("VMA")) { (packageName: String) ->
//        FViewModel(
//            packageName,
//            get(named("AR"))
//        )
//    }

    /**applist*/

    /**applist*/
//    viewModel {
//        AppListViewModel(get(named("APR")))
//    }

    /**appsetting*/

    /**appsetting*/
//    viewModel { (packageName: String) ->
//        AppSettingViewModel(packageName, get(named("APR")))
//    }

    /**service*/

    /**service*/
//    viewModel(named("VMS")) { (packageName: String) ->
//        FViewModel(
//            packageName,
//            get(named("SR"))
//        )
//    }

    /**info*/

    /**info*/
//    viewModel(named("VMI")) { (name: String, packageName: String, type: String) ->
//        InfoViewModel(
//            name,
//            packageName,
//            when (type) {
//                "alarm" -> {
//                    get(named("IRA"))
//                }
//                "service" -> {
//                    get(named("IRS"))
//                }
//                "wakelock" -> {
//                    get(named("IRW"))
//                }
//                else -> {
//                    get(named("IRW"))
//                }
//            }
//        )
//    }

    /**wakelock*/

//    /**wakelock*/
//    viewModel(named("VMW")) { (packageName: String) ->
//        FViewModel(
//            packageName,
//            get(named("WR"))
//        )
//    }


//    viewModel { (packageName: String) ->
//        ALWakeLockViewModel(
//            packageName,
//            get(named("WLR"))
//        )
//    }

//    viewModel { (packageName: String) ->
//        ALWakeLockViewModel(get(named("WLR")), packageName)
//    }
//    single {
//        DataRepository(
//            AppDatabase.getInstance(
//                BasicApp.context
//            )
//        )
//    }

//    /** InfoRepository */
//    single<InfoRepository>(named("IR")) { (type: String) ->
//        IInfoRepository(AppDatabase.getInstance(BasicApp.context).infoDao(), type)
//    }
//    /**info*/
//    viewModel(named("VMI")) { (name: String, packageName: String, type: String) ->
//        InfoViewModel(
//            name,
//            packageName,
//            get(named("IR")) { parametersOf(type) }
//        )
//    }
}