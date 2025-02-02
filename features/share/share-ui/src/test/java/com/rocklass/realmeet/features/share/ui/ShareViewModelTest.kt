package com.rocklass.realmeet.features.share.ui

import app.cash.turbine.test
import com.rocklass.realmeet.features.share.domain.usecase.FetchShareInfoUseCase
import com.rocklass.realmeet.features.share.ui.ShareViewModel
import com.rocklass.realmeet.features.share.ui.mapper.ShareInfoToUIModelMapper
import com.rocklass.realmeet.features.share.ui.model.ShareUIModel
import com.rocklass.realmeet.features.share.domain.model.ShareInfo
import com.rocklass.realmeet.features.share.ui.ShareViewModel.ShareState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever

class ShareViewModelTest {
    private lateinit var viewModel: ShareViewModel
    private val fetchShareInfoUseCase: FetchShareInfoUseCase = mock()
    private val shareInfoToUIModelMapper: ShareInfoToUIModelMapper = mock()

    @Before
    fun setup() {
        viewModel = ShareViewModel(fetchShareInfoUseCase, shareInfoToUIModelMapper)
    }

    @Test
    fun `init emits state DisplayShare when fetchShareInfoUseCase succeeds`() = runTest {
        // Given
        val shareInfo = ShareInfo(imageUrl = "https://test.com/image.jpg", shareText = "https://test.com/share")
        val shareUIModel = ShareUIModel(imageUrl = shareInfo.imageUrl, shareText = shareInfo.shareText)
        val result: Result<ShareInfo> = Result.success(shareInfo)

        whenever(fetchShareInfoUseCase()).thenAnswer { result }
        whenever(shareInfoToUIModelMapper(shareInfo)).thenReturn(shareUIModel)

        viewModel.state.test {
            // When
            viewModel.init()

            // Then
            assertEquals(ShareState.Initial, awaitItem())
            assertEquals(ShareState.DisplayShare(shareUIModel), awaitItem())
        }
    }

   @Test
   fun `init emits state Error when fetchShareInfoUseCase fails`() = runTest {
       // Given
       val result: Result<ShareInfo> = Result.failure(Exception("Network Error"))
       whenever(fetchShareInfoUseCase()).thenAnswer { result }

       viewModel.state.test {
           assertEquals(ShareState.Initial, awaitItem())

          // When
           viewModel.init()

           // Then
           val errorState = awaitItem()
           require(errorState is ShareState.Error)
           assertEquals("Network Error", errorState.message)
       }
   }

    @Test
    fun `share emits state Share`() = runTest {
        // Given
        val shareInfo = ShareInfo(imageUrl = "https://test.com/image.jpg", shareText = "https://test.com/share")
        val result: Result<ShareInfo> = Result.success(shareInfo)
        whenever(fetchShareInfoUseCase()).thenAnswer { result }
        val shareUIModel = ShareUIModel(imageUrl = "https://test.com/image.jpg", shareText = "https://test.com/share")
        whenever(shareInfoToUIModelMapper(shareInfo)).thenReturn(shareUIModel)

        viewModel.state.test {
            assertEquals(ShareState.Initial, awaitItem())
            viewModel.init()
            assertEquals(ShareState.DisplayShare(shareUIModel), awaitItem())

            // When
            viewModel.share()

            // Then
            assertEquals(ShareState.Share(shareUIModel), awaitItem())
        }
    }
}
