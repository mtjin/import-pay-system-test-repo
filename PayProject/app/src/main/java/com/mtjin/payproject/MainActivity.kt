package com.mtjin.payproject

import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var mainWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainWebView = findViewById<View>(R.id.webview) as WebView
        mainWebView.webViewClient = InicisWebViewClient(this)
        val settings = mainWebView.settings
        settings.javaScriptEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            val cookieManager =
                CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(mainWebView, true)
        }

        val intent = intent
        val intentData = intent.data

        if (intentData == null) {
            mainWebView.loadUrl("http://www.iamport.kr/demo")
//        	mainWebView.loadUrl("http://192.168.0.77:8888");
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            val url = intentData.toString()
            val flag = url.startsWith(APP_SCHEME)
            if (flag) {
                val redirectURL: String = url.substring(APP_SCHEME.length + 3)
                mainWebView.loadUrl(redirectURL)
            }
        }
    }

    companion object {
        private const val APP_SCHEME: String = "iamporttest"
    }
}
