package com.aspark.screenreader

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.util.Log
import com.aspark.screenreader.databinding.ActivityMainBinding

class MainActivityPresenter : Contract.Presenter {

    override fun checkServiceStatus(serviceList: List<AccessibilityServiceInfo>,
                                    binding: ActivityMainBinding, context: Context) {


        for (service in serviceList) {

            if ( context.packageName.startsWith(service.resolveInfo.serviceInfo.packageName) &&
                service.resolveInfo.serviceInfo.name.endsWith(MyAccessibilityService::class.simpleName!!)) {

                Log.d("Presenter", "checkServiceStatus: our accessibility service is running")

                binding.tvServiceStatus.text = context.resources.getString(R.string.service_running)
                binding.tvInfo.text = context.resources.getString(R.string.turn_off_service_info)

            }
        }
    }
}