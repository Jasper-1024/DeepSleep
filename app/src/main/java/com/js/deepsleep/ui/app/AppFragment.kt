package com.js.deepsleep.ui.app

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.deepsleep.databinding.FragmentAppBinding
import com.js.deepsleep.ui.mainactivity.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.qualifier.named


@KoinApiExtension
class AppFragment : Fragment() {

    private lateinit var binding: FragmentAppBinding

    private val viewModel: AppViewModel by viewModel(named("AppVm"))

    private val mainViewModel: MainViewModel by sharedViewModel(named("MainVm"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSubscription(viewModel.apps)
        addSubscription(mainViewModel.type)
        addSubscription(mainViewModel.query)
        addSubscription(mainViewModel.sort)
    }


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
        //分割线
        setItemDecoration(binding.appList)
        // 下拉刷新
        setSwipeRefreshLayout(binding.refresh)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        removeSubscription(viewModel.apps)
        removeSubscription(mainViewModel.type)
        removeSubscription(mainViewModel.query)
        removeSubscription(mainViewModel.sort)
    }

    //viewModel.list 添加订阅
    private fun <S> addSubscription(liveData: LiveData<S>) {
        viewModel.list.addSource(liveData) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                viewModel.list.postValue(list() ?: list())
            }
        }
    }

    //viewModel.list 取消订阅
    private fun <S> removeSubscription(liveData: LiveData<S>) {
        viewModel.list.removeSource(liveData)
    }

    //读取值
    private fun list() = viewModel.apps.value?.let { apps ->
        mainViewModel.type.value?.let { type ->
            mainViewModel.query.value?.let { query ->
                mainViewModel.sort.value?.let { sort ->
                    viewModel.getList(
                        apps, type, query, sort
                    )
                }
            }
        }
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

    // 分割线
    private fun setItemDecoration(recyclerView: RecyclerView) = recyclerView.addItemDecoration(
        DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
    )
}