package com.mtjin.payproject

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import java.net.URISyntaxException

open class InicisWebViewClient(private var activity: Activity) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (!url!!.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            var intent: Intent? = null
            return try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME) //IntentURI처리
                val uri = Uri.parse(intent.dataString)
                activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            } catch (ex: URISyntaxException) {
                false
            } catch (e: ActivityNotFoundException) {
                if (intent == null) return false
                if (handleNotFoundPaymentScheme(intent.scheme)) return true
                val packageName = intent.getPackage()
                if (packageName != null) {
                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                    return true
                }
                false
            }
        }

        return false
    }

    /**
     * @param scheme
     * @return 해당 scheme에 대해 처리를 직접 하는지 여부
     *
     * 결제를 위한 3rd-party 앱이 아직 설치되어있지 않아 ActivityNotFoundException이 발생하는 경우 처리합니다.
     * 여기서 handler되지않은 scheme에 대해서는 intent로부터 Package정보 추출이 가능하다면 다음에서 packageName으로 market이동합니다.
     */
    protected fun handleNotFoundPaymentScheme(scheme: String?): Boolean {
        //PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우
        if (PaymentScheme.ISP.equals(scheme, ignoreCase = true)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_ISP)
                )
            )
            return true
        } else if (PaymentScheme.BANKPAY.equals(scheme, ignoreCase = true)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_BANKPAY)
                )
            )
            return true
        }
        return false
    }
}