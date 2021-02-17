package com.js.deepsleep.ui.extend.fbase

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.js.deepsleep.R
import com.js.deepsleep.base.Type
import com.js.deepsleep.base.menuGone
import com.js.deepsleep.databinding.FragmentFbaseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


open class FBaseFragment : Fragment() {

    open val type: Type = Type.Wakelock

    lateinit var packageName: String

    private lateinit var binding: FragmentFbaseBinding

    open val viewModel: FBaseViewModel by viewModel(named("ExtendVm")) {
        parametersOf(packageName, type)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 获取 packageName
        packageName = arguments?.getString("packageName") ?: ""
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFbaseBinding.inflate(inflater, container, false)
        context ?: return binding.root
        //绑定
        binding.vm = viewModel
        binding.lifecycleOwner = this

        //fragment 对 toolbar 菜单操作
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(menu, setOf(R.id.menu_filter, R.id.search))
        super.onCreateOptionsMenu(menu, inflater)
    }
}