package com.seif.howtodrawpersontestapp.presentation.child.draw_screen

data class DrawScreenViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val uploadDrawSuccessfully:Boolean = false
)