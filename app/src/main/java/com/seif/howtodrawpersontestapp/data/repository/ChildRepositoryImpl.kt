package com.seif.howtodrawpersontestapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.seif.howtodrawpersontestapp.data.model.ChildDataModel
import com.seif.howtodrawpersontestapp.domain.repository.ChildRepository
import com.seif.howtodrawpersontestapp.util.SharedPrefs
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val sharedPrefs: SharedPrefs,
) : ChildRepository {
    override suspend fun uploadChildData(childDataModel: ChildDataModel) {
        try {
            val document = firestore.collection("childs").document()
            childDataModel.id = document.id
            document.set(childDataModel).await()
            sharedPrefs.put("child_id", childDataModel.id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun uploadDraw(draw: ByteArray) {
        try {
            val childId = sharedPrefs.get("child_id", String::class.java)
            val uri = storage.reference.child("images/$childId").putBytes(draw).await().storage.downloadUrl.await()
            val document = firestore.collection("childs").document(childId)
            document.update("drawnImage", uri.toString()).await()
            document.update("testDate", prepareTestDateAndTime()).await()

        } catch (e: Exception) {
            throw e
        }
    }

    private fun prepareTestDateAndTime():String {
        val currentDateTime = LocalDateTime.now()
        val formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        return "$formattedDate - $formattedTime"
    }

    override suspend fun fetchChildData(): ChildDataModel? {
        try {
            val childId = sharedPrefs.get("child_id", String::class.java)
            val document = firestore.collection("childs").document(childId).get().await()
            val childData = document.toObject(ChildDataModel::class.java)
            return childData
        } catch (e: Exception) {
            throw e
        }
    }

    override fun saveFinishingTestState(completed: Boolean) {
        sharedPrefs.put("finishTestState", completed)
    }

    override fun getFinishingTestState():Boolean {
        return sharedPrefs.get("finishTestState", Boolean::class.java)
    }

    override suspend fun fetchDrawnList(): List<ChildDataModel>? {
        try {
            val querySnapshot = firestore.collection("childs").get().await()
            val drawnList = arrayListOf<ChildDataModel>()
            for (document in querySnapshot) {
                val childData = document.toObject(ChildDataModel::class.java)
                drawnList.add(childData)
            }
            return drawnList
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun saveChildGrades(
        childId: String,
        totalGrade: String,
        mindAge: String,
        intelligenceGrade: String,
        intelligenceValue: String,
        gradeList: List<String>
    ) {
        try {
            val document = firestore.collection("childs").document(childId)
            document.update("totalGrade",totalGrade).await()
            document.update("mindAgeInMonths",mindAge).await()
            document.update("intelligenceGrade",intelligenceGrade).await()
            document.update("intelligenceValue",intelligenceValue).await()
            document.update("gradeList", gradeList).await()
        } catch (e: Exception) {
            throw e
        }
    }
}