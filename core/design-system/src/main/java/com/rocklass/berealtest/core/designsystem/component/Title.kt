package com.rocklass.berealtest.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 3,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        maxLines = maxLines,
    )
}

@Preview
@Composable
private fun TitlePreview() {
    Title(text = "This is a title!")
}