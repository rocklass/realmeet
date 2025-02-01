package com.rocklass.realmeet.features.capture.data

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CaptureEndpoint {
    @Multipart
    @POST("/capture")
    suspend fun sendCapture(@Part image: MultipartBody.Part)
}