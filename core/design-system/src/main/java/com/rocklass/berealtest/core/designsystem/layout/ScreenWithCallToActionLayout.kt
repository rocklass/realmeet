package com.rocklass.berealtest.core.designsystem.layout

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
import com.rocklass.berealtest.core.designsystem.component.Cta
import com.rocklass.berealtest.core.designsystem.component.Description
import com.rocklass.berealtest.core.designsystem.component.Title

@Composable
fun ScreenWithCallToActionLayout(
    title: String,
    description: String? = null,
    cta: String,
    onCtaClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Title(text = title)
        Spacer(modifier = Modifier.height(16.dp))
        description?.let {
            Description(text = it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Cta(text = cta) {
            onCtaClick()
        }
    }
}

@Preview
@Composable
private fun ScreenWithCallToActionLayoutPreview() {
    ScreenWithCallToActionLayout(
        title = "Title",
        description = "Description",
        cta = "CTA",
        onCtaClick = {},
    )
}