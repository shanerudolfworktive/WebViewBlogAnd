package com.shaneblog.www.webviewblogand

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.salomonbrys.kotson.int
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webview.addFunction("identity", this::renderIdentity)
        webview.addFunction("workforce", this::renderJob)
    }

    private fun renderIdentity(jObj: JsonObject?){
        val name = jObj?.get("name")?.string
        val age = jObj?.get("age")?.int

        webview.invokeJavascript("my name is $name and I am $age years old")
    }

    private fun renderJob(jObj: JsonObject?){
        val title = jObj?.get("title")?.string
        webview.invokeJavascript("I work as $title")
    }
}
