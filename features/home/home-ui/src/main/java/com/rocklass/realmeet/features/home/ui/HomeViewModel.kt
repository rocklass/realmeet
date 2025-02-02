package com.rocklass.realmeet.features.home.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.DisplayHome)
    val state: StateFlow<HomeState> = _state


    fun onNotificationPermissionGranted() {
        _state.update { HomeState.DisplayHome }
    }

    fun onNotificationPermissionDenied() {
        _state.update { HomeState.NotificationPermissionRequired }
    }

    sealed class HomeState {
        data object DisplayHome : HomeState()
        data object NotificationPermissionRequired : HomeState()
    }
}
