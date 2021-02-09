package com.js.deepsleep.ui.databinding

import androidx.recyclerview.widget.DiffUtil
import com.js.deepsleep.ui.databinding.item.BaseItem

class DiffCallback : DiffUtil.ItemCallback<BaseItem>() {
    /*Is it the same object*/
    override fun areItemsTheSame(
        oldItem: BaseItem,
        newItem: BaseItem
    ): Boolean {
        return oldItem.getID() == newItem.getID()
    }

    /*Whether the content is the same*/
    override fun areContentsTheSame(
        oldItem: BaseItem,
        newItem: BaseItem
    ): Boolean {
        return oldItem.getContent() == newItem.getContent()
    }
}