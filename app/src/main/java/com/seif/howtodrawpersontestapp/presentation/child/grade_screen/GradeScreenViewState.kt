package com.seif.howtodrawpersontestapp.presentation.child.grade_screen

import com.seif.howtodrawpersontestapp.data.model.ChildDataModel

data class GradeScreenViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val fetchChildData: ChildDataModel? = null
)