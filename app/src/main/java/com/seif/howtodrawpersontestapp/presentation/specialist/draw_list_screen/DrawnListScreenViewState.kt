package com.seif.howtodrawpersontestapp.presentation.specialist.draw_list_screen

import com.seif.howtodrawpersontestapp.data.model.ChildDataModel

data class DrawnListScreenViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val fetchDrawnList: List<ChildDataModel>? = null
)