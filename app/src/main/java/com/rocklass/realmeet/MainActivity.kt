package com.rocklass.realmeet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.rocklass.realmeet.core.navigation.Routes
import com.rocklass.realmeet.core.notification.NotificationType.LAUNCH_REAL_MEET
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDestination = if (intent.getStringExtra("notification_type") == LAUNCH_REAL_MEET) {
            Routes.CAPTURE
        } else {
            Routes.HOME
        }

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                RealMeetNavGraph(
                    navController = navController,
                    startDestination = startDestination,
                )
            }
        }
    }
}