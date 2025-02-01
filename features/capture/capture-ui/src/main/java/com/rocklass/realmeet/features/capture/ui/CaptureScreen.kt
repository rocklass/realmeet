package com.rocklass.realmeet.features.capture.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CaptureScreen(viewModel: CaptureViewModel = hiltViewModel()) {
    CameraPreview()
}

@Composable
internal fun CameraPreview() {
    Text("Camera Preview")
}
