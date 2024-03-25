package com.seif.howtodrawpersontestapp.domain.repository

import com.seif.howtodrawpersontestapp.data.model.ChildDataModel

interface ChildRepository {
    suspend fun uploadChildData(childDataModel: ChildDataModel)
    suspend fun uploadDraw(draw: ByteArray)
    suspend fun fetchChildData():ChildDataModel?
     fun saveFinishingTestState(completed: Boolean)
    fun getFinishingTestState():Boolean
    suspend fun fetchDrawnList():List<ChildDataModel>?
    suspend fun saveChildGrades(
        childId: String,
        childGrade: String,
        childIntelligence: String
    )
}