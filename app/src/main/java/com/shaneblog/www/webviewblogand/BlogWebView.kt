package com.shaneblog.www.webviewblogand

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * Created by shane1 on 1/23/18.
 */
class BlogWebView : WebView{
    constructor (context: Context) : super(context){ Initialize() }
    constructor (context: Context, attrs: AttributeSet): super(context, attrs) { Initialize() }
    constructor (context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) { Initialize() }

    var exposedFunctions = HashMap<String, (JsonObject?)->Unit>()

    fun Initialize(){
        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE;
        addJavascriptInterface(this, "AndroidApp")
    }

    @JavascriptInterface
    fun messageFromJS(message: String){
        Handler(context.mainLooper).post({
            val obj = JsonParser().parse(message).asJsonObject
            exposedFunctions[obj["functionName"]?.string]?.invoke(obj)
        })
    }

    fun exposeFunctionToJS(functionName: String, function: (JsonObject?) -> Unit){
        exposedFunctions.put(functionName, function)
    }

    fun invokeJavascript(message:String?){
        evaluateJavascript("render('$message')", null)
    }
}