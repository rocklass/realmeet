package com.rocklass.realmeet.features.capture.ui

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.rocklass.berealtest.core.designsystem.component.cta.Cta
import com.rocklass.berealtest.core.designsystem.component.cta.CtaUIModel
import com.rocklass.berealtest.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.berealtest.core.designsystem.component.title.TitleUIModel
import com.rocklass.berealtest.core.designsystem.layout.fullscreenloader.FullScreenLoader
import com.rocklass.berealtest.core.designsystem.layout.fullscreenloader.FullScreenLoaderUIModel
import com.rocklass.berealtest.core.designsystem.layout.screenwithcta.ScreenWithCTAUIModel
import com.rocklass.berealtest.core.designsystem.layout.screenwithcta.ScreenWithCallToActionLayout
import com.rocklass.realmeet.core.navigation.Routes.SHARE
import com.rocklass.realmeet.features.capture.ui.CaptureViewModel.CaptureState.*
import com.rocklass.realmeet.features.capture.ui.model.CaptureUIModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureScreen(
    viewModel: CaptureViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    CameraPermission(
        cameraPermissionState = cameraPermissionState,
        onPermissionGranted = viewModel::onPermissionGranted,
        onPermissionDenied = viewModel::onPermissionDenied,
    )

    val captureState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val onCapture = { viewModel.takePicture(context.applicationContext) }

    when (val state = captureState) {
        is Initial -> FullScreenLoader(
            uiModel = FullScreenLoaderUIModel(),
        )
        is DisplayCapturePreview -> CameraPreview(
            captureUIModel = state.uiModel,
            onCapture = onCapture,
            viewModel = viewModel
        )
        is CameraPermissionRequired -> CameraPermissionRequired {
            cameraPermissionState.launchPermissionRequest()
        }
        is Loading -> LoadingScreen()
        is Success -> onCaptureSuccess(
            navController = navController,
        )
        is Error -> ErrorScreen(
            errorMessage = state.message,
            onRetry = onCapture,
        )
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun CameraPermission(
    cameraPermissionState: PermissionState,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    LaunchedEffect(cameraPermissionState.status) {
        if (cameraPermissionState.status.isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }
}

@Composable
private fun CameraPermissionRequired(onRequestPermission: () -> Unit) {
    ScreenWithCallToActionLayout(
        uiModel = ScreenWithCTAUIModel(
            title = TitleUIModel(text = stringResource(R.string.camera_permission_required_title)),
            description = DescriptionUIModel(text = stringResource(R.string.camera_permission_required_description)),
            cta = CtaUIModel.Default(text = stringResource(R.string.camera_permission_required_button)),
        ),
    ){
        onRequestPermission()
    }
}

@Composable
private fun CameraPreview(
    captureUIModel: CaptureUIModel,
    onCapture: () -> Unit,
    viewModel: CaptureViewModel,
) {
    FullScreenLoader(
        uiModel = FullScreenLoaderUIModel(),
    )

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
        surfaceRequest?.let { request ->
            CameraXViewfinder(surfaceRequest = request)
        }
        Cta(
            uiModel = captureUIModel.cta,
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            onCapture()
        }
    }
}

@Composable
private fun LoadingScreen() {
    FullScreenLoader(
        uiModel = FullScreenLoaderUIModel(
            text = stringResource(R.string.camera_capture_send_loading),
        ),
    )
}

fun onCaptureSuccess(navController: NavHostController) {
    navController.navigate(SHARE)
}

@Composable
fun ErrorScreen(
    errorMessage: String?,
    onRetry: () -> Unit,
) {
    ScreenWithCallToActionLayout(
        uiModel = ScreenWithCTAUIModel(
            title = TitleUIModel(text = stringResource(R.string.camera_capture_error)),
            description = errorMessage?.let { DescriptionUIModel(text = errorMessage) },
            cta = CtaUIModel.Default(text = stringResource(R.string.camera_capture_retry)),
        ),
    ) {
        onRetry()
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraPermissionRequiredPreview() {
    CameraPermissionRequired {}
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(
        errorMessage = "Error message",
        onRetry = {},
    )
}
