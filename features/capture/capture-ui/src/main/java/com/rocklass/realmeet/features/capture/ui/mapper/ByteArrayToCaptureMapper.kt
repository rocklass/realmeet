package com.rocklass.realmeet.features.capture.ui.mapper

import com.rocklass.realmeet.features.capture.domain.model.Capture
import javax.inject.Inject

class ByteArrayToCaptureMapper @Inject constructor() : (ByteArray) -> Capture {
    override fun invoke(byteArray: ByteArray): Capture {
        return Capture(
            image = byteArray,
        )
    }
}