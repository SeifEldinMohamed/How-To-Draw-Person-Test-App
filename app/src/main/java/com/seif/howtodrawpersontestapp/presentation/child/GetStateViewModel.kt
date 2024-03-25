package com.seif.howtodrawpersontestapp.presentation.child

import androidx.lifecycle.ViewModel
import com.seif.howtodrawpersontestapp.domain.usecase.GetFinishingTestStateUseCase
import com.seif.howtodrawpersontestapp.domain.usecase.SaveFinishingTestStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetStateViewModel @Inject constructor(
    private val getFinishingTestSateUseCase: GetFinishingTestStateUseCase
):ViewModel() {

    fun getFinishingTestSate():Boolean {
       return getFinishingTestSateUseCase()
    }
}