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
        checkServiceStatus()


    }

    private fun checkServiceStatus() {

        binding.tvServiceStatus.text = resources.getString(R.string.service_not_running)
        binding.tvInfo.text = resources.getString(R.string.turn_on_service_info)

        val manager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val serviceList = manager
            .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)

        MainActivityPresenter().checkServiceStatus(serviceList,binding,this)
    }

    override fun onResume() {
        super.onResume()

        Log.i("MainActivity", "onResume: ")
        checkServiceStatus()

    }
}