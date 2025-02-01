package com.rocklass.realmeet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rocklass.realmeet.features.capture.ui.CaptureScreen

@Composable
fun RealMeetNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "capture") {
        composable("capture") {
            CaptureScreen()
        }
    }
}