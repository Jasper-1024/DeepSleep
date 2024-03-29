package com.js.deepsleep.ui.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.js.deepsleep.R
import com.js.deepsleep.tools.menuGone
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingViewModel by viewModel(named("SettingVm"))

    // 备份
    private val backupRAFR =
        registerForActivityResult(CreateDocument("todo/todo")) { uri ->
            if (uri != null) {
                viewModel.backup(uri)
            }
        }

    // 恢复
    private val restoreRAFR =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.restore(uri)
            }
        }

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
                    backupRAFR.launch("DeepSleep-Backup-${getData()}.json")
                    true
                }
        }
        // 恢复按钮
        val restore: Preference? = findPreference("restore")
        if (restore != null) {
            restore.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    restoreRAFR.launch("*/*")
                    true
                }
        }
    }

    // 关闭多余 toolbar 菜单
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(menu, setOf(R.id.menu_filter, R.id.search))
        super.onCreateOptionsMenu(menu, inflater)
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
}