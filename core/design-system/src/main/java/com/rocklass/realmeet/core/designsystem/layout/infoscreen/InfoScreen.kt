package com.rocklass.realmeet.core.designsystem.layout.infoscreen

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
import com.rocklass.realmeet.core.designsystem.component.cta.Cta
import com.rocklass.realmeet.core.designsystem.component.description.Description
import com.rocklass.realmeet.core.designsystem.component.title.Title
import com.rocklass.realmeet.core.designsystem.component.cta.CtaUIModel
import com.rocklass.realmeet.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.realmeet.core.designsystem.component.title.TitleUIModel
import com.rocklass.realmeet.core.designsystem.layout.screenwithcta.ScreenWithCTA

@Composable
fun InfoScreen(
    uiModel: InfoScreenUIModel,
    onCtaClick: () -> Unit,
) {
    ScreenWithCTA(
        content = {
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
            }
        },
        cta = {
            Cta(uiModel = uiModel.cta) {
                onCtaClick()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun InfoScreenPreview() {
    InfoScreen(
        uiModel = InfoScreenUIModel(
            title = TitleUIModel(text = "Title"),
            description = DescriptionUIModel(text = "Description"),
            cta = CtaUIModel.Default(text = "CTA"),
        ),
        onCtaClick = {},
    )
}