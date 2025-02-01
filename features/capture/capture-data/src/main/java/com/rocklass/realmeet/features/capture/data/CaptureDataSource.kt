package com.rocklass.realmeet.features.capture.data

import okhttp3.MultipartBody
import javax.inject.Inject

class CaptureDataSource @Inject constructor(
    private val captureEndpoint: CaptureEndpoint,
) {
    suspend fun sendCapture(image: MultipartBody.Part) {
        return captureEndpoint.sendCapture(image)
    }
}