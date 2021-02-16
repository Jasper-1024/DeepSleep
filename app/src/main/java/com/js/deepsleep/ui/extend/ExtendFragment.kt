package com.js.deepsleep.ui.extend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.js.deepsleep.R


class ExtendFragment : Fragment() {

    private lateinit var packageName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 获取 packageName
        packageName = arguments?.getString("packageName") ?: ""
        return inflater.inflate(R.layout.fragment_extend, container, false)
    }
}