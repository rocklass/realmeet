package com.rocklass.realmeet.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocklass.realmeet.features.home.domain.usecase.InitNotificationsUseCase
import com.rocklass.realmeet.features.home.domain.usecase.SendNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val initNotificationsUseCase: InitNotificationsUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.DisplayHome)
    val state: StateFlow<HomeState> = _state


    fun onNotificationPermissionGranted() {
        viewModelScope.launch {
            initNotificationsUseCase().fold(
                onSuccess = {
                    _state.update { HomeState.DisplayHome }
                },
                onFailure = { exception ->
                    _state.update { HomeState.Error(exception.message) }
                }
            )
        }
    }

    fun onNotificationPermissionDenied() {
        _state.update { HomeState.NotificationPermissionRequired }
    }

    fun sendNotification() {
        viewModelScope.launch {
            sendNotificationUseCase().fold(
                onSuccess = {
                    _state.update { HomeState.DisplayHome }
                },
                onFailure = { exception ->
                    _state.update { HomeState.Error(exception.message) }
                }
            )
        }
    }

    sealed class HomeState {
        data object DisplayHome : HomeState()
        data object NotificationPermissionRequired : HomeState()
        data class Error(val message: String?) : HomeState()
    }
}
