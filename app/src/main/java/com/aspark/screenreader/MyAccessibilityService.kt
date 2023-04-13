package com.aspark.screenreader

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.*

class MyAccessibilityService : AccessibilityService() {

    private var totalText = StringBuilder()

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {

        Log.i(
            "MyAccessibilityService",
            "onAccessibilityEvent: ${p0.toString()}"
        )

        p0!!.source?.apply {

            val packageName = this.packageName.toString()

            val appInfo = packageManager.getAppInfo(packageName, 0)
            val appName = packageManager.getApplicationLabel(appInfo).toString()
            Log.d("AccessService", "onAccessibilityEvent APP NAME: $appName")


            CoroutineScope(Dispatchers.Default).launch {

                val finalText = traverseChildViews(this@apply,appName)
                withContext(Dispatchers.IO) {
                    Log.i("AccessService", "onAccessibilityEvent:  coroutine finalText= $finalText")
                    saveScreenText(appName, finalText.toString())
                }
            }

        }
    }

    //recursive function to extract text from every view
    private fun traverseChildViews(info: AccessibilityNodeInfo?, appName: String) : StringBuilder?{

        if (info == null)
            return null

        if (info.text != null && info.text.isNotEmpty()) {

            Log.d("AccessService", "traverseChildViews: ${info.text}")
            totalText.append(info.text.toString())
            totalText.append(" ")

            //saveScreenText(appName,info.text.toString())

        }

        for (i in 0 until info.childCount) {

            val childNode = info.getChild(i)
            traverseChildViews(childNode,appName)
        }

        return totalText
    }

    private fun saveScreenText(appName: String, text: String) {

        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(appName,text)
        editor.apply()

        Log.d("AccessService", "saveScreenText: Saved ${getSharedPreferences(packageName,
            MODE_PRIVATE).getString(appName,"Kuch Nahi Mila bro")}")

        totalText.clear()

    }

    override fun onInterrupt() {
        Log.e("MyAccessibilityService", "onInterrupt: Accessibility Service Interrupted")
    }


    private fun PackageManager.getAppInfo(packageName: String, flag: Int): ApplicationInfo {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
           return getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(flag.toLong())
            )
        } else {
            @Suppress("DEPRECATION")
           return getApplicationInfo(packageName, flag)
        }

    }
}


