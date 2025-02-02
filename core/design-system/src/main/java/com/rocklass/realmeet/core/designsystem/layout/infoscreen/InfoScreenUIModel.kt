package com.rocklass.realmeet.core.designsystem.layout.infoscreen

import com.rocklass.realmeet.core.designsystem.component.cta.CtaUIModel
import com.rocklass.realmeet.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.realmeet.core.designsystem.component.title.TitleUIModel

data class InfoScreenUIModel(
    val title: TitleUIModel,
    val description: DescriptionUIModel? = null,
    val cta: CtaUIModel,
)