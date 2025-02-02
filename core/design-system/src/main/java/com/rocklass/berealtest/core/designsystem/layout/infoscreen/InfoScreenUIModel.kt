package com.rocklass.berealtest.core.designsystem.layout.infoscreen

import com.rocklass.berealtest.core.designsystem.component.cta.CtaUIModel
import com.rocklass.berealtest.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.berealtest.core.designsystem.component.title.TitleUIModel

data class InfoScreenUIModel(
    val title: TitleUIModel,
    val description: DescriptionUIModel? = null,
    val cta: CtaUIModel,
)