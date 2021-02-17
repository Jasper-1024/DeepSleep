package com.js.deepsleep.ui.databinding

import android.R
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.js.deepsleep.data.db.entity.AppInfo
import com.js.deepsleep.ui.databinding.item.BaseItem


object DataBind {
    @JvmStatic
    @BindingAdapter("loadIcon")
    fun loadIcon(imageView: ImageView, appInfo: AppInfo) {
        val options = RequestOptions()
            .error(R.drawable.sym_def_app_icon)
            .placeholder(R.drawable.sym_def_app_icon)
        val uri = Uri.parse("android.resource://" + appInfo.packageName + "/" + appInfo.icon)
        Glide.with(imageView.context)
            .applyDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
            .load(uri)
            .apply(options)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("items")
    fun setRecyclerViewItems(
        recyclerView: RecyclerView,
        items: List<BaseItem>?
    ) {
        var adapter = (recyclerView.adapter as? RecycleAdapter)
        if (adapter == null) {
            adapter = RecycleAdapter()
            recyclerView.adapter = adapter
        }
        // items 为空 orEmpty 返回空实例
        adapter.submitList(items.orEmpty())
    }
}

object Converter {

    @InverseMethod("stringToSet")
    @JvmStatic
    fun setToString(values: Set<String>?): String {
        return if (values == null || values.isEmpty()) {
            "\n"
        } else {
            var tmp = ""
            values.forEach {
                tmp += "$it\n"
            }
//            tmp = tmp.substring(0, tmp.length - 1)
            tmp
        }
    }

    @JvmStatic
    fun stringToSet(value: String?): Set<String> {
        return if (value == null || value == "\n") {
            mutableSetOf()
        } else {
            value.split("\n")
                .filter { it.matches(Regex("[^\n ]+")) }
                .toSet()
//            value.split('\n').toSet()
        }
    }


}






