package com.shaneblog.www.webviewblogand

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.loadUrl("https://shanerudolfworktive.github.io/WebViewBlogHtml/WebViewBlogPart2")
        webview.exposeFunctionToJS("identity", this::renderIdentity)
        webview.exposeFunctionToJS("workforce", this::renderJob)
    }

    private fun renderIdentity(params: JsonObject?){
        val name = params?.get("name")?.string
        val age = params?.get("age")?.int
        webview.invokeJavascript("my name is $name and I am $age years old")
    }

    private fun renderJob(params: JsonObject?){
        val title = params?.get("title")?.string
        webview.invokeJavascript("I work as $title")
    }
}
