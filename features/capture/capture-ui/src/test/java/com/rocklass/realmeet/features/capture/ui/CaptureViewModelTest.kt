package com.rocklass.realmeet.features.capture.ui

import androidx.camera.core.SurfaceRequest
import app.cash.turbine.test
import com.rocklass.realmeet.core.camera.CameraManager
import com.rocklass.realmeet.features.capture.domain.model.Capture
import com.rocklass.realmeet.features.capture.domain.usecase.SendCaptureUseCase
import com.rocklass.realmeet.features.capture.ui.CaptureViewModel.CaptureState
import com.rocklass.realmeet.features.capture.ui.CaptureViewModel.CaptureState.CameraPermissionRequired.withLoading
import com.rocklass.realmeet.features.capture.ui.mapper.ByteArrayToCaptureMapper
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CaptureViewModelTest {

    private lateinit var viewModel: CaptureViewModel
    private val sendCaptureUseCase: SendCaptureUseCase = mock()
    private val byteArrayToCaptureMapper: ByteArrayToCaptureMapper = mock()
    private val cameraManager: CameraManager = mock()

    @Before
    fun setUp() {
        viewModel = CaptureViewModel(sendCaptureUseCase, byteArrayToCaptureMapper, cameraManager)
    }

    @Test
    fun `bindToCamera emits state DisplayCapturePreview when camera is initialized`() = runTest {
        // Given
        val surfaceRequest: SurfaceRequest = mock()
        whenever(cameraManager.initializeCamera(any(), any())).thenReturn(surfaceRequest)

        viewModel.state.test {
            // When
            viewModel.bindToCamera(mock(), mock())

            // Then
            assertEquals(CaptureState.Initial, awaitItem())
            assertEquals(CaptureState.DisplayCapturePreview(), awaitItem())
        }
    }

    @Test
    fun `takePicture emits state Loading and then Success when sendCaptureUseCase succeeds`() = runTest {
        // Given
        val byteArray: ByteArray = byteArrayOf(1, 2, 3)
        val capture = mock<Capture>()
        whenever(byteArrayToCaptureMapper(byteArray)).thenReturn(capture)
        whenever(sendCaptureUseCase(any())).thenAnswer { Result.success(Unit) }

        whenever(cameraManager.takePicture(any(), any(), any())).thenAnswer {
            val onSuccess: (ByteArray) -> Unit = it.getArgument(1)
            onSuccess(byteArray)
        }

        viewModel.state.test {
            // When
            viewModel.onPermissionGranted()
            viewModel.takePicture(mock())

            // Then
            assertEquals(CaptureState.Initial, awaitItem())
            assertEquals(CaptureState.DisplayCapturePreview(), awaitItem())
            assertEquals(CaptureState.DisplayCapturePreview().withLoading(), awaitItem())
            assertEquals(CaptureState.Loading, awaitItem())
            assertEquals(CaptureState.Success, awaitItem())
        }
    }

    @Test
    fun `onPermissionGranted emits state DisplayCapturePreview`() = runTest {
        viewModel.state.test {
            // When
            viewModel.onPermissionGranted()

            // Then
            assertEquals(CaptureState.Initial, awaitItem())
            assertEquals(CaptureState.DisplayCapturePreview(), awaitItem())
        }
    }

    @Test
    fun `onPermissionDenied emits state CameraPermissionRequired`() = runTest {
        viewModel.state.test {
            viewModel.onPermissionDenied()

            assertEquals(CaptureState.Initial, awaitItem())
            assertEquals(CaptureState.CameraPermissionRequired, awaitItem())
        }
    }
}
