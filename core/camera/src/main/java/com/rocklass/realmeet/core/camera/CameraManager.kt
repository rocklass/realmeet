package com.rocklass.realmeet.core.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class CameraManager @Inject constructor() {
    private val previewUseCase = Preview.Builder().build()
    private val imageCaptureUseCase = ImageCapture.Builder().build()

    suspend fun initializeCamera(context: Context, lifecycleOwner: LifecycleOwner): SurfaceRequest {
        val processCameraProvider = withContext(Dispatchers.IO) {
            ProcessCameraProvider.getInstance(context).get()
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        processCameraProvider.unbindAll()
        processCameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            previewUseCase,
            imageCaptureUseCase,
        )

        return suspendCancellableCoroutine { cont ->
            previewUseCase.setSurfaceProvider { surfaceRequest -> cont.resume(surfaceRequest) }
        }
    }

    fun takePicture(
        context: Context,
        onSuccess: (ByteArray) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val callback = object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val buffer = image.planes[0].buffer
                val byteArray = ByteArray(buffer.remaining())
                buffer.get(byteArray)
                image.close()
                onSuccess(byteArray)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }

        imageCaptureUseCase.takePicture(
            ContextCompat.getMainExecutor(context),
            callback,
        )
    }
}
