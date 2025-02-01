package com.rocklass.berealtest.core.designsystem.component

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Cta(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            text = text,
            maxLines = maxLines,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Preview
@Composable
private fun CtaPreview() {
    Cta(text = "CTA") {}
}