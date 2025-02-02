package com.rocklass.berealtest.core.designsystem.component.description

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Description(
    uiModel: DescriptionUIModel,
    modifier: Modifier = Modifier.padding(horizontal = 8.dp),
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = uiModel.text,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        maxLines = maxLines,
    )
}

@Preview(showBackground = true)
@Composable
private fun DescriptionPreview() {
    Description(
        uiModel = DescriptionUIModel(
            text = "This is a description!",
        ),
    )
}