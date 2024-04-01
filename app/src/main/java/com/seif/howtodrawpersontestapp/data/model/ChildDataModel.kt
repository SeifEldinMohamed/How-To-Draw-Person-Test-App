package com.seif.howtodrawpersontestapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChildDataModel(
    var id: String = "",
    val name:String = "",
    val dayOfBirth:String = "",
    val monthOfBirth:String = "",
    val yearOfBirth:String = "",
    val drawnImage: String = "",
    val testDate: String = "",
    val totalGrade: String ="",
    val mindAgeInMonths: String = "",
    val intelligenceGrade: String = "",
    val intelligenceValue: String = "",
    val gradeList: List<String> = emptyList(),
):Parcelable
