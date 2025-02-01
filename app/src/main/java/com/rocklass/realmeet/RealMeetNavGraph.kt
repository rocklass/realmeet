package com.rocklass.realmeet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rocklass.realmeet.core.navigation.Routes.CAPTURE
import com.rocklass.realmeet.core.navigation.Routes.SHARE
import com.rocklass.realmeet.features.capture.ui.CaptureScreen
import com.rocklass.realmeet.features.postcapture.ui.ShareScreen

@Composable
fun RealMeetNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = CAPTURE) {
        composable(CAPTURE) {
            CaptureScreen(navController = navController)
        }
        composable(SHARE) {
            ShareScreen()
        }
    }
}