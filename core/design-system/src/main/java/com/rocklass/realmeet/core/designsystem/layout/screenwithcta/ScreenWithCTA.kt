package com.rocklass.realmeet.core.designsystem.layout.screenwithcta

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rocklass.realmeet.core.designsystem.component.cta.Cta
import com.rocklass.realmeet.core.designsystem.component.cta.CtaUIModel
import com.rocklass.realmeet.core.designsystem.component.description.Description
import com.rocklass.realmeet.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.realmeet.core.designsystem.component.title.Title
import com.rocklass.realmeet.core.designsystem.component.title.TitleUIModel

@Composable
fun ScreenWithCTA(
    content: @Composable () -> Unit,
    cta: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            content()
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            cta()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenWithCTAPreview() {
    ScreenWithCTA(
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
            ) {
                Title(uiModel = TitleUIModel(text = "Title"))
                Description(uiModel = DescriptionUIModel(text = "Description"))
            }
        },
        cta = {
            Cta(uiModel = CtaUIModel.Default(text = "CTA")) {}
        }
    )
}
