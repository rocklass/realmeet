package com.rocklass.realmeet.features.capture.data.mapper

import com.rocklass.realmeet.features.capture.domain.model.Capture
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class CaptureToMultipartImageMapper @Inject constructor() : (Capture) -> MultipartBody.Part {
    override fun invoke(capture: Capture): MultipartBody.Part {
        val body = capture.image.toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", "capture.jpg", body)
    }
}