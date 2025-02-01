package com.rocklass.realmeet.features.capture.domain

import com.rocklass.realmeet.features.capture.domain.model.Capture

interface CaptureRepository {
    suspend fun sendCapture(capture: Capture)
}