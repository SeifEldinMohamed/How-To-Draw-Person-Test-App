package com.seif.howtodrawpersontestapp.presentation.child.baby_info_screen

data class BabyInfoViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val uploadChildDataSuccessfully:Boolean = false
)