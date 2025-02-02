package com.rocklass.realmeet.features.capture.ui

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocklass.berealtest.core.designsystem.component.cta.CtaUIModel
import com.rocklass.realmeet.features.capture.domain.usecase.SendCaptureUseCase
import com.rocklass.realmeet.features.capture.ui.CaptureViewModel.CaptureState.Success.withLoading
import com.rocklass.realmeet.features.capture.ui.mapper.ByteArrayToCaptureMapper
import com.rocklass.realmeet.features.capture.ui.model.CaptureUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val sendCaptureUseCase: SendCaptureUseCase,
    private val byteArrayToCaptureMapper: ByteArrayToCaptureMapper,
) : ViewModel() {
    private val _state = MutableStateFlow<CaptureState>(CaptureState.Initial)
    val state: StateFlow<CaptureState> = _state

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
        }
    }

    private val imageCaptureUseCase = ImageCapture.Builder().build()

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner = lifecycleOwner,
            cameraSelector = CameraSelector.Builder().build(),
            cameraPreviewUseCase,
            imageCaptureUseCase,
        )

        try { awaitCancellation() } finally { processCameraProvider.unbindAll() }
    }

    fun takePicture(appContext: Context) {
        val currentState = _state.value 
        if (currentState is CaptureState.DisplayCapturePreview) {
            _state.update { currentState.withLoading() }
            val callback = object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val buffer = image.planes[0].buffer
                    val byteArray = ByteArray(buffer.remaining())
                    buffer.get(byteArray)
                    image.close()
                    sendCapture(byteArray)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(
                        CaptureViewModel::class.simpleName,
                        "Photo capture failed: ${exception.message}",
                        exception
                    )
                    _state.update { CaptureState.Error(exception.message) }
                }
            }
            imageCaptureUseCase.takePicture(
                ContextCompat.getMainExecutor(appContext),
                callback,
            )
        }
    }

    fun onPermissionGranted() {
        _state.update { CaptureState.DisplayCapturePreview() }
    }

    fun onPermissionDenied() {
        _state.update { CaptureState.CameraPermissionRequired }
    }

    private fun sendCapture(byteArray: ByteArray) {
        _state.update { CaptureState.Loading }
        val capture = byteArrayToCaptureMapper(byteArray)
        viewModelScope.launch {
            sendCaptureUseCase(capture).fold(
                onSuccess = {
                    _state.update { CaptureState.Success }
                },
                onFailure = { exception ->
                    _state.update { CaptureState.Error(exception.message ?: "Unknown error") }
                },
            )
        }
    }

    sealed class CaptureState {
        data object Initial : CaptureState()
        data class DisplayCapturePreview(val uiModel: CaptureUIModel = CaptureUIModel()) : CaptureState()
        data object CameraPermissionRequired : CaptureState()
        data object Loading : CaptureState()
        data object Success : CaptureState()
        data class Error(val message: String?) : CaptureState()

        fun DisplayCapturePreview.withLoading() = copy(uiModel = uiModel.copy(cta = CtaUIModel.Loading))
    }
}
