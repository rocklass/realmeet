package com.rocklass.realmeet.features.capture.ui.model

import com.rocklass.berealtest.core.designsystem.component.cta.CtaUIModel

data class CaptureUIModel(
    val cta: CtaUIModel = CtaUIModel.Default(text = "Capture"),
)