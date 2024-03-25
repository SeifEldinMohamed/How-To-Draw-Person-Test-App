package com.seif.howtodrawpersontestapp.presentation.child.grade_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.howtodrawpersontestapp.domain.usecase.FetchChildDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradeViewModel @Inject constructor(
    private val fetchChildDataUseCase: FetchChildDataUseCase
):ViewModel() {
    private val _gradeScreenState = MutableStateFlow<GradeScreenViewState?>(null)
    val gradeScreenState: StateFlow<GradeScreenViewState?> = _gradeScreenState

    fun getGrade() {
        _gradeScreenState.value = GradeScreenViewState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = fetchChildDataUseCase()
                _gradeScreenState.value = GradeScreenViewState(isLoading = false)
                result?.let {
                    _gradeScreenState.value = GradeScreenViewState(fetchChildData = it)
                }

            } catch (e:Exception) {
                _gradeScreenState.value = GradeScreenViewState(errorMessage = e.message.toString())
            }
        }
    }
}