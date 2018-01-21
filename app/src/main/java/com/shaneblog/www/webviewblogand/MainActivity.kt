package com.shaneblog.www.webviewblogand

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.settings.javaScriptEnabled = true
        webview.addJavascriptInterface(this, "AndroidApp")
        webview.loadUrl("file:///android_asset/WebViewBlog.html")
    }

    @JavascriptInterface
    public fun getOSVersion(message: String){
        runOnUiThread{
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
            var buildVersion = "Android : " + Build.VERSION.RELEASE + "(" + Build.VERSION.SDK_INT + ")";
            webview.evaluateJavascript(
                    "renderOSVersion('" + buildVersion + "')",
                    null)
        }
    }
}
