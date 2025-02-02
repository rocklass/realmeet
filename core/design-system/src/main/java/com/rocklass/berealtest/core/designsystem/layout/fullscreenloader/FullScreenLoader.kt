package com.rocklass.berealtest.core.designsystem.layout.fullscreenloader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FullScreenLoader(
    uiModel: FullScreenLoaderUIModel,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            uiModel.text?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FullScreenLoaderPreview() {
    FullScreenLoader(
        uiModel = FullScreenLoaderUIModel(),
    )
}

@Preview(showBackground = true)
@Composable
private fun FullScreenLoaderWithTextPreview() {
    FullScreenLoader(
        uiModel = FullScreenLoaderUIModel(
            text = "Loading...",
        ),
    )
}