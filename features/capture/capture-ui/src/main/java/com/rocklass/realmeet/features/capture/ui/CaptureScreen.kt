package com.rocklass.realmeet.features.capture.ui

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rocklass.berealtest.core.designsystem.component.Cta
import com.rocklass.berealtest.core.designsystem.layout.ScreenWithCallToActionLayout

@Composable
fun CaptureScreen(viewModel: CaptureViewModel = hiltViewModel()) {
    CameraPreview(viewModel)
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraPreview(viewModel: CaptureViewModel) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraPreviewContent(viewModel)
    } else {
        CameraPermissionRequired(cameraPermissionState)
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun CameraPermissionRequired(cameraPermissionState: PermissionState) {
    ScreenWithCallToActionLayout(
        title = stringResource(R.string.camera_permission_required_title),
        description = stringResource(R.string.camera_permission_required_description),
        cta = stringResource(R.string.camera_permission_required_button),
    ) {
        cameraPermissionState.launchPermissionRequest()
    }
}

@Composable
private fun CameraPreviewContent(viewModel: CaptureViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let { request ->
        Box {
            CameraXViewfinder(
                surfaceRequest = request,
            )
            Cta(
                text = stringResource(R.string.camera_capture_button),
                modifier = Modifier
                    .align(Alignment.BottomCenter),
            ) {
                viewModel.takePicture(context.applicationContext)
            }
        }
    }
}