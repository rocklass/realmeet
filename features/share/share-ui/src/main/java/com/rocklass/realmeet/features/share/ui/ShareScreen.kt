package com.rocklass.realmeet.features.share.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rocklass.berealtest.core.designsystem.component.cta.Cta
import com.rocklass.berealtest.core.designsystem.component.cta.CtaUIModel
import com.rocklass.berealtest.core.designsystem.component.description.DescriptionUIModel
import com.rocklass.berealtest.core.designsystem.component.image.Image
import com.rocklass.berealtest.core.designsystem.component.image.ImageUIModel
import com.rocklass.berealtest.core.designsystem.component.title.TitleUIModel
import com.rocklass.berealtest.core.designsystem.layout.fullscreenloader.FullScreenLoader
import com.rocklass.berealtest.core.designsystem.layout.fullscreenloader.FullScreenLoaderUIModel
import com.rocklass.berealtest.core.designsystem.layout.infoscreen.InfoScreenUIModel
import com.rocklass.berealtest.core.designsystem.layout.infoscreen.InfoScreen
import com.rocklass.berealtest.core.designsystem.layout.screenwithcta.ScreenWithCTA
import com.rocklass.realmeet.features.share.ui.ShareViewModel.ShareState.DisplayShare
import com.rocklass.realmeet.features.share.ui.ShareViewModel.ShareState.Error
import com.rocklass.realmeet.features.share.ui.ShareViewModel.ShareState.Initial
import com.rocklass.realmeet.features.share.ui.model.ShareUIModel

@Composable
fun ShareScreen(
    viewModel: ShareViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.init()
    }

    val shareState by viewModel.state.collectAsStateWithLifecycle()

    when (val state = shareState) {
        is Initial -> FullScreenLoader(
            uiModel = FullScreenLoaderUIModel(),
        )
        is DisplayShare -> DisplayShareScreen(
            uiModel = state.uiModel,
            onShare = viewModel::share,
        )
        is Error -> ErrorScreen(
            errorMessage = state.message,
            onRetry = viewModel::init,
        )
    }
}

@Composable
private fun DisplayShareScreen(
    uiModel: ShareUIModel,
    onShare: () -> Unit,
) {
    ScreenWithCTA(
        content = {
            Image(
                uiModel = ImageUIModel(imageUrl = uiModel.imageUrl),
            )
        },
        cta = {
            Cta(
                uiModel = CtaUIModel.Default(text = "Share"),
                onClick = onShare,
            )
        },
    )
}

@Composable
fun ErrorScreen(
    errorMessage: String?,
    onRetry: () -> Unit,
) {
    InfoScreen(
        uiModel = InfoScreenUIModel(
            title = TitleUIModel(text = stringResource(R.string.share_loading_error)),
            description = errorMessage?.let { DescriptionUIModel(text = errorMessage) },
            cta = CtaUIModel.Default(text = stringResource(R.string.share_loading_retry)),
        ),
    ) {
        onRetry()
    }
}

@Preview(showBackground = true)
@Composable
private fun DisplayShareScreenPreview() {
    DisplayShareScreen(
        uiModel = ShareUIModel(
            imageUrl = "https://www.ifolor.ch/content/dam/ifolor/inspire-gallery/inspirationen/selbstportraet-selfie/inspire_selbstportraet_selfie_1200px_header.jpg.transform/w1440/q60/image.jpg?inspire_selbstportraet_selfie_1200px_header.jpg",
            shareUrl = "",
        ),
        onShare = {},
    )
}