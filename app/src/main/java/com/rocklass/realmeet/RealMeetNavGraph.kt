package com.rocklass.realmeet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rocklass.realmeet.core.navigation.Routes.CAPTURE
import com.rocklass.realmeet.core.navigation.Routes.HOME
import com.rocklass.realmeet.core.navigation.Routes.SHARE
import com.rocklass.realmeet.features.capture.ui.CaptureScreen
import com.rocklass.realmeet.features.home.ui.HomeScreen
import com.rocklass.realmeet.features.share.ui.ShareScreen

@Composable
fun RealMeetNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = HOME) {
        composable(HOME) {
            HomeScreen(navController = navController)
        }
        composable(CAPTURE) {
            CaptureScreen(navController = navController)
        }
        composable(SHARE) {
            ShareScreen()
        }
    }
}