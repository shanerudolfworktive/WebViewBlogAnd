package com.shaneblog.www.webviewblogand

import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.string
import com.github.salomonbrys.kotson.toJson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * Created by shane1 on 1/23/18.
 */
class BlogWebView : WebView{
    constructor (context : Context) : super(context){ Initialize() }
    constructor (context: Context, attrs: AttributeSet): super(context, attrs) { Initialize() }
    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) { Initialize() }

    var functions = HashMap<String, (JsonObject?)->Unit>()

    fun Initialize(){
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE;
        addJavascriptInterface(this, "AndroidApp")
//        loadUrl("https://shanerudolfworktive.github.io/WebViewBlogHtml/")
        loadUrl("file:///android_asset/WebViewBlog.html")
    }

    @JavascriptInterface
    fun messageFromJS(message: String){
        Handler(context.mainLooper).post({
            val obj = JsonParser().parse(message).asJsonObject
            functions[obj["functionName"]?.string]?.invoke(obj)
        })
    }

    fun addFunction(functionName: String, function: (JsonObject?) -> Unit){
        functions.put(functionName, function)
    }

    fun invokeJavascript(message:String?){
        evaluateJavascript("render('$message')", null)
    }
}