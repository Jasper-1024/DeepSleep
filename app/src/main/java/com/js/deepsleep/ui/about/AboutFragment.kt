package com.js.deepsleep.ui.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import com.js.deepsleep.R
import com.js.deepsleep.tools.menuGone
import com.js.deepsleep.databinding.FragmentAboutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


class AboutFragment : Fragment() {

    private val viewModel: AboutViewModel by viewModel(named("AboutVm"))

    private lateinit var binding: FragmentAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.vm = viewModel
        binding.sourcecode.movementMethod = LinkMovementMethod.getInstance()
        return binding.root
    }

    // 关闭多余 toolbar 菜单
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(menu, setOf(R.id.menu_filter, R.id.search))
        super.onCreateOptionsMenu(menu, inflater)
    }
}