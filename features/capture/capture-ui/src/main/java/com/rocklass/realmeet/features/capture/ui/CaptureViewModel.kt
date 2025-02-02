package com.rocklass.realmeet.features.capture.ui

import android.content.Context
import androidx.camera.core.SurfaceRequest
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocklass.berealtest.core.designsystem.component.cta.CtaUIModel
import com.rocklass.realmeet.core.camera.CameraManager
import com.rocklass.realmeet.features.capture.domain.usecase.SendCaptureUseCase
import com.rocklass.realmeet.features.capture.ui.CaptureViewModel.CaptureState.Success.withLoading
import com.rocklass.realmeet.features.capture.ui.mapper.ByteArrayToCaptureMapper
import com.rocklass.realmeet.features.capture.ui.model.CaptureUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaptureViewModel @Inject constructor(
    private val sendCaptureUseCase: SendCaptureUseCase,
    private val byteArrayToCaptureMapper: ByteArrayToCaptureMapper,
    private val cameraManager: CameraManager,
) : ViewModel() {
    private val _state = MutableStateFlow<CaptureState>(CaptureState.Initial)
    val state: StateFlow<CaptureState> = _state

    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    fun bindToCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            val surfaceRequest = cameraManager.initializeCamera(context, lifecycleOwner)
            _surfaceRequest.update { surfaceRequest }
            _state.update { CaptureState.DisplayCapturePreview() }
        }
    }

    fun takePicture(context: Context) {
        val currentState = _state.value
        if (currentState is CaptureState.DisplayCapturePreview) {
            _state.update { currentState.withLoading() }
            cameraManager.takePicture(
                context = context,
                onSuccess = { byteArray -> sendCapture(byteArray) },
                onError = { exception ->
                    _state.update { CaptureState.Error(exception.message ?: "Unknown error") }
                }
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
