package com.rocklass.realmeet.features.capture.ui

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
    ) {
        Text(text = stringResource(R.string.camera_permission_required_title))
        Text(text = stringResource(R.string.camera_permission_required_description))
        Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
            Text(stringResource(R.string.camera_permission_required_button))
        }
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
        CameraXViewfinder(
            surfaceRequest = request,
        )
    }
}