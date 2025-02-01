package com.rocklass.realmeet.features.capture.domain.usecase

import com.rocklass.realmeet.features.capture.domain.CaptureRepository
import com.rocklass.realmeet.features.capture.domain.model.Capture
import javax.inject.Inject

class SendCaptureUseCase @Inject constructor(
    private val searchRepository: CaptureRepository,
): suspend (Capture) -> Result<Unit> {
    override suspend fun invoke(capture: Capture): Result<Unit> {
        return try {
            Result.success(searchRepository.sendCapture(capture))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}