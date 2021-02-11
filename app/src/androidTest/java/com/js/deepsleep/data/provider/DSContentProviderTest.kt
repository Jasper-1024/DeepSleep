package com.js.deepsleep.data.provider

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class DSContentProviderTest {

    private var mContentResolver: ContentResolver? = null
    private val authority = "com.js.deepsleep"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mContentResolver = context.contentResolver
    }

    @Test
    fun call() {
        val method = "test"
        val url = Uri.parse("content://$authority")

        val extras = Bundle()
        extras.putString("Test", "Test")

        val bundle = mContentResolver?.call(url, method, null, extras)
        //if (bundle != null) {
        //    LogUtil.d("test1", bundle.toString())
        //}
        assertEquals(bundle!!.getString("Test"), "Test")
    }
}