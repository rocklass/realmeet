package com.rocklass.realmeet.core.designsystem.component.cta

sealed class CtaUIModel {
    data class Default(
        val text: String,
        val maxLines: Int = 1,
    ) : CtaUIModel()

    data object Loading : CtaUIModel()
}