package com.rocklass.realmeet.features.share.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocklass.realmeet.features.share.domain.usecase.FetchShareInfoUseCase
import com.rocklass.realmeet.features.share.ui.mapper.ShareInfoToUIModelMapper
import com.rocklass.realmeet.features.share.ui.model.ShareUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val fetchShareInfoUseCase: FetchShareInfoUseCase,
    private val shareInfoToUIModelMapper: ShareInfoToUIModelMapper,
): ViewModel() {
    private val _state = MutableStateFlow<ShareState>(ShareState.Initial)
    val state: StateFlow<ShareState> = _state

    fun init() {
        viewModelScope.launch {
            fetchShareInfoUseCase().fold(
                onSuccess = { shareInfo ->
                    _state.update { ShareState.DisplayShare(shareInfoToUIModelMapper(shareInfo)) }
                },
                onFailure = { exception ->
                    Log.e("ShareViewModel", "Error fetching share info", exception)
                    _state.update { ShareState.Error(exception.message) }
                }
            )
        }
    }

    fun share() {
        //TODO("Not yet implemented")
    }


    sealed class ShareState {
        data object Initial : ShareState()
        data class DisplayShare(val uiModel: ShareUIModel) : ShareState()
        data class Error(val message: String?) : ShareState()
    }
}