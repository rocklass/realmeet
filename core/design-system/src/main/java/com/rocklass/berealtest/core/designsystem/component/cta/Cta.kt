package com.rocklass.berealtest.core.designsystem.component.cta

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Cta(
    uiModel: CtaUIModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        when (uiModel) {
            is CtaUIModel.Default -> {
                Text(
                    text = uiModel.text,
                    maxLines = uiModel.maxLines,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            is CtaUIModel.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.inversePrimary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CtaDefaultPreview() {
    Cta(uiModel = CtaUIModel.Default(text = "Button")) {}
}

@Preview
@Composable
private fun CtaLoadingPreview() {
    Cta(uiModel = CtaUIModel.Loading) {}
}