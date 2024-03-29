package com.js.deepsleep.tools

import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.os.RemoteException
import android.os.UserHandle
import android.provider.Settings
import android.widget.Toast
import com.js.deepsleep.BasicApp
import com.js.deepsleep.R
import java.lang.reflect.Method

private const val TAG = "XProvider"
private const val PER_USER_RANGE = 100000

class XProvider {
    companion object {
        @Throws(RemoteException::class, IllegalArgumentException::class)
        fun call(context: Context?, method: String?, extras: Bundle?): Bundle? {
            return if ((context != null) and (method != null) and (extras != null)) {
                handle(context!!, method!!, extras!!)
            } else null

        }

        // handle method call
        private fun handle(context: Context, method: String, extras: Bundle): Bundle {
            return when (method) {
                XProviderMethods.forceStop -> xForceStop(context, extras)
                else -> Bundle()
            }
        }
    }
}

// all method's name
object XProviderMethods {
    const val forceStop: String = "forceStop"
}

// XProvider.uri
fun getURI(): Uri {
    return Settings.System.CONTENT_URI
}

private fun getUserId(uid: Int): Int {
    return try {
        // public static final int getUserId(int uid)
        val method =
            UserHandle::class.java.getDeclaredMethod("getUserId", Int::class.javaPrimitiveType)
        method.invoke(null, uid) as Int
    } catch (ex: Throwable) {
        return uid / PER_USER_RANGE
    }
}

private fun xForceStop(context: Context, extras: Bundle): Bundle {
    val packageName = extras.getString("packageName")
    val uid = extras.getInt("uid")
    val userid = getUserId(uid)

    forceStop(context, packageName!!, userid)
    return forceStop(context, packageName, userid)
}

// force stop app
@Throws(Throwable::class)
private fun forceStop(context: Context, packageName: String, userid: Int): Bundle {
    // Access activity manager as system user
    val id = Binder.clearCallingIdentity()
    val result = Bundle()
    try {
        // public void forceStopPackageAsUser(String packageName, int userId)
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val mForceStop: Method = am.javaClass.getMethod(
            "forceStopPackageAsUser",
            String::class.java,
            Int::class.javaPrimitiveType
        )
        mForceStop.invoke(am, packageName, userid)
        //if success
        result.putString("packageName", packageName)
    } finally {
        Binder.restoreCallingIdentity(id)
    }
    return result
}

fun callStopApp(packageName: String, uid: Int): Boolean {

    val args = Bundle()
    args.putString("packageName", packageName)
    args.putInt("uid", uid)

    val uri = getURI()
    val contentResolver = BasicApp.context.contentResolver

    val result: Bundle? = contentResolver.call(uri, "DeepSleep", XProviderMethods.forceStop, args)

    return result?.getString("packageName") == packageName
}