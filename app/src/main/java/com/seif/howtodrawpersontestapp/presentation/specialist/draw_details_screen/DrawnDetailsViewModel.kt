package com.seif.howtodrawpersontestapp.presentation.specialist.draw_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seif.howtodrawpersontestapp.domain.usecase.SaveChildGradeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawnDetailsViewModel @Inject constructor(
    private val saveChildGradeUseCase: SaveChildGradeUseCase
):ViewModel() {
    private val _drawnDetailsScreenState = MutableStateFlow<DrawnDetailsViewState?>(null)
    val drawnDetailsViewState: StateFlow<DrawnDetailsViewState?> = _drawnDetailsScreenState

     fun saveChildGrade(
         childId: String,
         totalGrade: String,
         mindAge: String,
         intelligenceGrade: String,
         intelligenceValue: String,
         gradeList: List<String>
    ) {
        _drawnDetailsScreenState.value = DrawnDetailsViewState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                saveChildGradeUseCase(
                    childId,
                    totalGrade,
                    mindAge,
                    intelligenceGrade,
                    intelligenceValue,
                    gradeList
                )
                _drawnDetailsScreenState.value = DrawnDetailsViewState(isLoading = false)
                _drawnDetailsScreenState.value = DrawnDetailsViewState(savedChildGrade = true)

            } catch (e: Exception) {
                _drawnDetailsScreenState.value =
                    DrawnDetailsViewState(errorMessage = e.message.toString())
            }
        }
    }

}