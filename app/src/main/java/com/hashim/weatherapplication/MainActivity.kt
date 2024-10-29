package com.hashim.weatherapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hashim.weatherapplication.core.utils.NotificationWorkManager
import com.hashim.weatherapplication.home.ui.views.BottomNavigationBar
import com.hashim.weatherapplication.ui.theme.WeatherApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApplicationTheme {
                BottomNavigationBar()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            subscribeWorkManager()
        }
    }

    private val pushNotificationPermissionLauncher = registerForActivityResult(RequestPermission()) { granted ->
        if (granted){
            subscribeWorkManager()
        }
    }

    private fun subscribeWorkManager(){
        val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorkManager>(20, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(notificationRequest)
    }
}