package com.rocklass.berealtest.core.designsystem.layout.screenwithcta

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rocklass.berealtest.core.designsystem.component.cta.Cta
import com.rocklass.berealtest.core.designsystem.component.description.Description
import com.rocklass.berealtest.core.designsystem.component.title.Title
import com.rocklass.berealtest.core.designsystem.component.cta.CtaUIModel
import com.rocklass.berealtest.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.berealtest.core.designsystem.component.title.TitleUIModel

@Composable
fun ScreenWithCallToActionLayout(
    uiModel: ScreenWithCTAUIModel,
    onCtaClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Title(uiModel = uiModel.title)
        Spacer(modifier = Modifier.height(16.dp))
        uiModel.description?.let {
            Description(uiModel = it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Cta(uiModel = uiModel.cta) {
            onCtaClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenWithCallToActionLayoutPreview() {
    ScreenWithCallToActionLayout(
        uiModel = ScreenWithCTAUIModel(
            title = TitleUIModel(text = "Title"),
            description = DescriptionUIModel(text = "Description"),
            cta = CtaUIModel.Default(text = "CTA"),
        ),
        onCtaClick = {},
    )
}