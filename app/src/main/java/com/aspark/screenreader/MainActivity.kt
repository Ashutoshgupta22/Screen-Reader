package com.aspark.screenreader

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityManager
import com.aspark.screenreader.databinding.ActivityMainBinding
import com.google.android.material.elevation.SurfaceColors

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set color of system statusBar same as ActionBar
        val color = SurfaceColors.SURFACE_2.getColor(this)
        window.statusBarColor = color

        //checking if our accessibility service is running
        val manager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val serviceList = manager
            .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)

        for (service in serviceList) {

            if (service.resolveInfo.serviceInfo.packageName == packageName &&
                    service.resolveInfo.serviceInfo.name == MyAccessibilityService::class.simpleName) {

                Log.d("MainActivity", "onCreate: our accessibility service is running")
                binding.tvServiceStatus.text = resources.getString(R.string.service_running)
                binding.tvInfo.text = resources.getString(R.string.turn_off_service_info)

            }
        }


    }
}