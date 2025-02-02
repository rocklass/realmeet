package com.rocklass.realmeet.features.home.ui

import app.cash.turbine.test
import com.rocklass.realmeet.features.home.domain.usecase.InitNotificationsUseCase
import com.rocklass.realmeet.features.home.domain.usecase.SendNotificationUseCase
import com.rocklass.realmeet.features.home.ui.HomeViewModel.HomeState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val initNotificationsUseCase: InitNotificationsUseCase = mock()
    private val sendNotificationUseCase: SendNotificationUseCase = mock()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(initNotificationsUseCase, sendNotificationUseCase)
    }

    @Test
    fun `onNotificationPermissionGranted emits state DisplayHome and init notifications`() = runTest {
        // Given
        whenever(initNotificationsUseCase.invoke()).thenAnswer { Result.success(Unit) }

        viewModel.state.test {
            // When
            viewModel.onNotificationPermissionGranted()

            // Then
            assertEquals(HomeState.DisplayHome, awaitItem())
            verify(initNotificationsUseCase).invoke()
        }
    }

    @Test
    fun `onPermissionDenied emits state NotificationPermissionRequired`() = runTest {
        viewModel.state.test {
            assertEquals(HomeState.DisplayHome, awaitItem())

            // When
            viewModel.onNotificationPermissionDenied()

            // Then
            assertEquals(HomeState.NotificationPermissionRequired, awaitItem())
        }
    }

    @Test
    fun `sendNotification sends notification`() = runTest {
        // Given
        whenever(sendNotificationUseCase.invoke()).thenAnswer { Result.success(Unit) }

        // When
        viewModel.sendNotification()

        // Then
        verify(sendNotificationUseCase).invoke()
    }
}