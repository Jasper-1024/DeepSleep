package com.js.deepsleep.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.js.deepsleep.R
import com.js.deepsleep.base.menuGone
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.qualifier.named
import java.util.*

@KoinApiExtension
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingViewModel by viewModel(named("SettingVm"))

    private val readJson: Int = 42
    private val saveJson: Int = 43

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        //主题切换
        val themePreference =
            findPreference<ListPreference>("theme_list")
        if (themePreference != null) {
            themePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    val themeOption = newValue as String
                    ThemeHelper.applyTheme(themeOption)
                    true
                }
        }

        // 备份按钮
        val backup: Preference? = findPreference("backup")
        if (backup != null) {
            backup.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    // 创建备份文件
                    createFile("*/*", "DeepSleep-Backup-${getData()}.json")
                    true
                }
        }
        // 恢复按钮
        val restore: Preference? = findPreference("restore")
        if (restore != null) {
            restore.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
//                    Toast.makeText(activity, "restore", Toast.LENGTH_LONG).show()
                    getJson()
                    true
                }
        }
    }

    // 关闭多余 toolbar 菜单
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(menu, setOf(R.id.menu_filter, R.id.search))
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        // 读取文件 恢复
        if (requestCode == readJson && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                viewModel.restore(uri)
            }
        }
        // 保存文件 备份
        if (requestCode == saveJson && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                viewModel.backup(uri)
            }
        }
    }

    // saf 创建文件
    private fun createFile(mimeType: String, fileName: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeType
            putExtra(Intent.EXTRA_TITLE, fileName)
        }
        // startActivityForResult 中处理
        startActivityForResult(intent, saveJson)
    }

    // 现在时间
    private fun getData(): String {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH) + 1
        val date: Int = c.get(Calendar.DATE)
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)
        return "$year:$month:$date-$hour:$minute"
    }

    private fun getJson() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(intent, readJson)
    }
}