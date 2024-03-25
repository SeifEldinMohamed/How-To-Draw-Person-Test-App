package com.seif.howtodrawpersontestapp.presentation.child.baby_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.howtodrawpersontestapp.data.model.ChildDataModel
import com.seif.howtodrawpersontestapp.domain.usecase.UploadChildDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BabyInfoViewModel @Inject constructor(
    private val uploadChildDataUseCase: UploadChildDataUseCase
):ViewModel() {
    private val _babyInfoState = MutableStateFlow<BabyInfoViewState?>(null)
    val babyInfoState: StateFlow<BabyInfoViewState?> = _babyInfoState

    fun uploadChildData(childDataModel: ChildDataModel) {
        _babyInfoState.value = BabyInfoViewState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uploadChildDataUseCase(childDataModel)
                _babyInfoState.value = BabyInfoViewState(isLoading = false)
                _babyInfoState.value = BabyInfoViewState(uploadChildDataSuccessfully = true)

            } catch (e:Exception) {
                _babyInfoState.value = BabyInfoViewState(errorMessage = e.message.toString())
            }
        }
    }
}