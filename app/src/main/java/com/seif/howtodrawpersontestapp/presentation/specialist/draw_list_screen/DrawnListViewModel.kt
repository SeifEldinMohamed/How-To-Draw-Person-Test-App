package com.seif.howtodrawpersontestapp.presentation.specialist.draw_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.howtodrawpersontestapp.domain.usecase.FetchChildDataUseCase
import com.seif.howtodrawpersontestapp.domain.usecase.FetchDrawnListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawnListViewModel @Inject constructor(
    private val fetchDrawnListUseCase: FetchDrawnListUseCase
):ViewModel() {
    private val _drawnListScreenState = MutableStateFlow<DrawnListScreenViewState?>(null)
    val drawnListScreenState: StateFlow<DrawnListScreenViewState?> = _drawnListScreenState

    init {
        getDrawList()
    }

    private fun getDrawList() {
        _drawnListScreenState.value = DrawnListScreenViewState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = fetchDrawnListUseCase()
                _drawnListScreenState.value = DrawnListScreenViewState(isLoading = false)
                result?.let {
                    _drawnListScreenState.value = DrawnListScreenViewState(fetchDrawnList = it)
                }

            } catch (e:Exception) {
                _drawnListScreenState.value = DrawnListScreenViewState(errorMessage = e.message.toString())
            }
        }
    }
}