package com.rocklass.realmeet.features.capture.data

import com.rocklass.realmeet.features.capture.data.mapper.CaptureToMultipartImageMapper
import com.rocklass.realmeet.features.capture.domain.CaptureRepository
import com.rocklass.realmeet.features.capture.domain.model.Capture
import javax.inject.Inject

class CaptureRepositoryImpl @Inject constructor(
    private val captureDataSource: CaptureDataSource,
    private val captureToMultipartImageMapper: CaptureToMultipartImageMapper,
): CaptureRepository {
    override suspend fun sendCapture(capture: Capture) {
        val image = captureToMultipartImageMapper(capture)
        captureDataSource.sendCapture(image = image)
    }
}