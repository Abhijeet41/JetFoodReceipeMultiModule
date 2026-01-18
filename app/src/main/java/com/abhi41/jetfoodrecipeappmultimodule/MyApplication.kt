package com.abhi41.jetfoodrecipeappmultimodule

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import android.webkit.WebView
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            WebView.setWebContentsDebuggingEnabled(false)
            WebView(this)
        } catch (e: Exception) {
            Log.e("WebViewInit", "WebView init failed", e)
        }
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = Application.getProcessName()
            if (processName != packageName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }

}