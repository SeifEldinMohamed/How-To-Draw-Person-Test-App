package com.seif.howtodrawpersontestapp.presentation.specialist.draw_details_screen


data class DrawnDetailsViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val savedChildGrade: Boolean = false
)