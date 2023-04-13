package com.aspark.screenreader

import android.accessibilityservice.AccessibilityService
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MyAccessibilityService : AccessibilityService() {


    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {

        Log.i("MyAccessibilityService",
            "onAccessibilityEvent: ${p0.toString()}")

        p0!!.source?.apply {

            val packageName = this.packageName
            Log.d("AccessService", "onAccessibilityEvent: $packageName")

            traverseChildViews(this)

        }
    }

    private fun traverseChildViews(info: AccessibilityNodeInfo?) {

        if (info == null)
            return

        if (info.text != null && info.text.isNotEmpty()){

            Log.d("AccessService", "traverseChildViews: ${info.text}")
        }

        for (i in 0 until info.childCount ) {

            val childNode = info.getChild(i)
            traverseChildViews(childNode)
        }

    }

    override fun onInterrupt() {
        Log.e("MyAccessibilityService", "onInterrupt: Accessibility Service Interrupted" )
    }
}


