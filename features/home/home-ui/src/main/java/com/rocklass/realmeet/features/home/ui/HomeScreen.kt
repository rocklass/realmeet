package com.rocklass.realmeet.features.home.ui

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rocklass.realmeet.core.designsystem.component.cta.CtaUIModel
import com.rocklass.realmeet.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.realmeet.core.designsystem.component.title.TitleUIModel
import com.rocklass.realmeet.core.designsystem.layout.infoscreen.InfoScreen
import com.rocklass.realmeet.core.designsystem.layout.infoscreen.InfoScreenUIModel
import com.rocklass.realmeet.core.navigation.Routes
import com.rocklass.realmeet.features.home.ui.HomeViewModel.HomeState.DisplayHome
import com.rocklass.realmeet.features.home.ui.HomeViewModel.HomeState.NotificationPermissionRequired

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val notificationPermissionState: PermissionState? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)
    } else {
        null
    }

    notificationPermissionState?.let {
        NotificationPermission(
            notificationPermissionState = notificationPermissionState,
            onPermissionGranted = viewModel::onNotificationPermissionGranted,
            onPermissionDenied = viewModel::onNotificationPermissionDenied,
        )
    }

    val homeState by viewModel.state.collectAsStateWithLifecycle()

    when (homeState) {
        is DisplayHome -> {
            InfoScreen(
                uiModel = InfoScreenUIModel(
                    title = TitleUIModel(text = stringResource(R.string.home_title)),
                    description = DescriptionUIModel(text = stringResource(R.string.home_description)),
                    cta = CtaUIModel.Default(text = stringResource(R.string.home_button)),
                ),
                onCtaClick = {
                    navController.navigate(Routes.CAPTURE)
                },
            )
        }
        is NotificationPermissionRequired -> NotificationPermissionRequired {
            notificationPermissionState?.launchPermissionRequest()
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun NotificationPermission(
    notificationPermissionState: PermissionState?,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    notificationPermissionState?.let {
        LaunchedEffect(notificationPermissionState.status) {
            if (notificationPermissionState.status.isGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}

@Composable
private fun NotificationPermissionRequired(onRequestPermission: () -> Unit) {
    InfoScreen(
        uiModel = InfoScreenUIModel(
            title = TitleUIModel(text = stringResource(R.string.home_permission_required_title)),
            description = DescriptionUIModel(text = stringResource(R.string.home_permission_required_description)),
            cta = CtaUIModel.Default(text = stringResource(R.string.home_permission_required_button)),
        ),
    ){
        onRequestPermission()
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationPermissionRequiredPreview() {
    NotificationPermissionRequired {}
}
