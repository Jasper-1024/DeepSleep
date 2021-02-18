package com.js.deepsleep.ui.settings

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.js.deepsleep.BasicApp
import com.js.deepsleep.R
import com.js.deepsleep.base.LogUtil
import com.js.deepsleep.base.getSetting
import com.js.deepsleep.data.repository.backup.AppBackup
import com.js.deepsleep.data.repository.backup.BackupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.io.*

@KoinApiExtension
class SettingViewModel : ViewModel(), KoinComponent {
    private val backupR: BackupRepository by inject(named("backupR"))

    // 保存备份
    fun backup(uri: Uri) {
        viewModelScope.launch(Dispatchers.Default) {
            val back = getBackup()//返回 AppBackup
//            LogUtil.d("test1", "$back")
            try {
                saveFile(uri, BasicApp.gson.toJson(back))//保存文件
                toast(BasicApp.context.getString(R.string.backup))
            } catch (e: Throwable) {
                LogUtil.e("backup", "saveFile: $e")//打印错误
                toast(BasicApp.context.getString(R.string.backupErr))
            }
        }
    }

    // 恢复备份
    fun restore(uri: Uri) {
        viewModelScope.launch(Dispatchers.Default) {
            val str =
                try {
                    getString(uri)
                } catch (e: Throwable) {
                    LogUtil.e("backup", "getString: $e")//打印错误
                    toast(BasicApp.context.getString(R.string.restoreErr))
                    ""
                }
            if (str != "") {
                jsonToAppBackup(str)?.let { restoreBackup(it) }
            }
            toast(BasicApp.context.getString(R.string.restore))
        }
    }

    //json 转换为 AppBackup
    private fun jsonToAppBackup(str: String): AppBackup? {
        val type = object : TypeToken<AppBackup>() {}.type
        return BasicApp.gson.fromJson(str, type)
    }

    //存入
    @Suppress("BlockingMethodInNonBlockingContext")
    @Throws(IOException::class)
    private suspend fun saveFile(uri: Uri, str: String) = withContext(Dispatchers.IO) {
        BasicApp.context.contentResolver.openFileDescriptor(uri, "w")?.use { it ->
            // use{} lets the document provider know you're done by automatically closing the stream
            FileOutputStream(it.fileDescriptor).use { file ->
                file.write(
                    str.toByteArray()
                )
            }
        }
    }

    // 从文件读取 json
    @Suppress("BlockingMethodInNonBlockingContext")
    @Throws(IOException::class)
    private suspend fun getString(uri: Uri): String = withContext(Dispatchers.IO) {
        val stringBuilder = StringBuilder()
        BasicApp.context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return@withContext stringBuilder.toString()
    }

    // 获取备份 AppBackup
    private suspend fun getBackup(): AppBackup {
        return backupR.getBackup(getSetting("ExtendEnable"))
    }

    // 恢复 AppBackup
    private suspend fun restoreBackup(appBackup: AppBackup) {
        backupR.restoreBackup(appBackup)
    }

    private suspend fun toast(str: String) = withContext(Dispatchers.Main) {
        Toast.makeText(BasicApp.context, str, Toast.LENGTH_LONG).show()
    }
}