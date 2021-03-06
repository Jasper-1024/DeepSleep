package com.js.deepsleep.ui.extend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.js.deepsleep.R


class ExtendFragment : Fragment() {

    private lateinit var packageName: String
    private lateinit var adapter: AppAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 获取 packageName
        packageName = arguments?.getString("packageName") ?: ""
        return inflater.inflate(R.layout.fragment_extend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = AppAdapter(this, packageName)
        viewPager = view.findViewById(R.id.app_pager)
        viewPager.offscreenPageLimit = 1
        (viewPager.getChildAt(0) as RecyclerView).layoutManager!!.isItemPrefetchEnabled = false
        viewPager.adapter = adapter

        val tabLayout = view.findViewById<TabLayout>(R.id.app_tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabText(position)
        }.attach()
    }


    private fun getTabText(position: Int): String {
        return when (position) {
            0 -> getString(R.string.wakelock)
            1 -> getString(R.string.alarm)
            else -> getString(R.string.wakelock)
        }
    }
}