package com.seif.howtodrawpersontestapp.domain.model

data class ChildDomainModel(
    var id: String,
    val name:String,
    val dayOfBirth:String,
    val monthOfBirth:String,
    val yearOfBirth:String,
    val drawnImage: ByteArray,
    val testDate: String,
    val totalGrade: String,
    val intelligenceGrade: String
)
