package com.js.deepsleep.ui.databinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.js.deepsleep.BR
import com.js.deepsleep.ui.databinding.item.BaseItem

class ViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    // 绑定
    fun bind(item: BaseItem?) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}