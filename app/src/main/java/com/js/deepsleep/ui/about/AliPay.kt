package com.js.deepsleep.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.js.deepsleep.R
import com.js.deepsleep.base.LogUtil
import java.net.URLEncoder

class AliPay(private var context: Context) {
    private val ALIPAY_SHOP = "https://qr.alipay.com/tsx168537dnhbsjucjfvl8f"//商户
    private val ALIPAY_PERSON =
        "https://qr.alipay.com/fkx16881wxcpvxbnwqkur21?t=1613570875134"//个人(支付宝里面我的二维码)

    fun jumpAlipay() {
        openAliPay2Pay(ALIPAY_SHOP)
    }

    private fun openAliPay2Pay(qrCode: String) {
        openAlipayPayPage(context, qrCode)
    }

    private fun openAlipayPayPage(context: Context, qrcode: String): Boolean {
        var encodeedQrcode = qrcode
        try {
            encodeedQrcode = URLEncoder.encode(qrcode, "utf-8")
        } catch (e: Exception) {
            LogUtil.d("Alipay", "$e")
        }
        try {
            val aliPayQr =
                "alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=$encodeedQrcode"
            val url = aliPayQr + "%3F_s%3Dweb-other&_t=" + System.currentTimeMillis()
            openUri(context, url)
            return true
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.donateErr), Toast.LENGTH_LONG).show()
            LogUtil.d("Alipay", "$e")
        }
        return false
    }

    private fun openUri(context: Context, s: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(s))
        context.startActivity(intent)
    }
}