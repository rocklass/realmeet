package com.rocklass.realmeet.core.designsystem.component.title

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Title(
    uiModel: TitleUIModel,
    modifier: Modifier = Modifier.padding(horizontal = 8.dp),
    maxLines: Int = 3,
) {
    Text(
        text = uiModel.text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        maxLines = maxLines,
    )
}

@Preview(showBackground = true)
@Composable
private fun TitlePreview() {
    Title(
        uiModel = TitleUIModel(
            text = "This is a title!",
        ),
    )
}