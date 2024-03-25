package com.seif.howtodrawpersontestapp.presentation.child.draw_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.howtodrawpersontestapp.data.model.ChildDataModel
import com.seif.howtodrawpersontestapp.domain.usecase.SaveFinishingTestStateUseCase
import com.seif.howtodrawpersontestapp.domain.usecase.UploadChildDataUseCase
import com.seif.howtodrawpersontestapp.domain.usecase.UploadDrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawScreenViewModel @Inject constructor(
    private val uploadDrawUseCase: UploadDrawUseCase,
    private val saveFinishingTestStateUseCase: SaveFinishingTestStateUseCase
):ViewModel() {
    private val _drawScreenState = MutableStateFlow<DrawScreenViewState?>(null)
    val drawScreenState: StateFlow<DrawScreenViewState?> = _drawScreenState

    fun uploadDraw(draw: ByteArray) {
        _drawScreenState.value = DrawScreenViewState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uploadDrawUseCase(draw)
                _drawScreenState.value = DrawScreenViewState(isLoading = false)
                _drawScreenState.value = DrawScreenViewState(uploadDrawSuccessfully = true)
            } catch (e:Exception) {
                _drawScreenState.value = DrawScreenViewState(errorMessage = e.message.toString())
            }
        }
    }

    fun saveFinishingTestSate(completed: Boolean) {
        saveFinishingTestStateUseCase(completed)
    }
}