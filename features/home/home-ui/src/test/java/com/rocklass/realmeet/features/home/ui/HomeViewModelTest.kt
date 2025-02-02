package com.rocklass.realmeet.features.home.ui

import app.cash.turbine.test
import com.rocklass.realmeet.features.home.ui.HomeViewModel.HomeState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        viewModel = HomeViewModel()
    }

    @Test
    fun `onNotificationPermissionGranted emits state DisplayHome`() = runTest {
        viewModel.state.test {
            // When
            viewModel.onNotificationPermissionGranted()

            // Then
            assertEquals(HomeState.DisplayHome, awaitItem())
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
}