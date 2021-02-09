package com.js.deepsleep.ui.app

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.deepsleep.databinding.FragmentAppBinding
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import org.koin.core.qualifier.named


@KoinApiExtension
class AppFragment : Fragment() {

    private lateinit var binding: FragmentAppBinding

    val viewModel: AppViewModel by inject(named("AppVm"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAppBinding.inflate(inflater, container, false)
        context ?: return binding.root

        //绑定 vm
        binding.vm = viewModel
        //绑定声明周期
        binding.lifecycleOwner = this
        // 下拉刷新
        setSwipeRefreshLayout(binding.refresh)


        return binding.root
    }


    //SwipeRefresh
    private fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setDistanceToTriggerSync(600)
        //color
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        // 关闭刷新
        swipeRefreshLayout.setOnRefreshListener {
            // 同步应用列表
            runBlocking {
                viewModel.syncApp()
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }
}