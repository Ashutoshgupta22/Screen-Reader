package com.aspark.screenreader

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import com.aspark.screenreader.databinding.ActivityMainBinding

interface Contract {

    interface Model {
    }

    interface Presenter{

        fun checkServiceStatus(serviceList: List<AccessibilityServiceInfo>,
        binding: ActivityMainBinding, context: Context)
    }

    interface View {

    }
}