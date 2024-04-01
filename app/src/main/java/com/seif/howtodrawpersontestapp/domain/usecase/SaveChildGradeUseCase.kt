package com.seif.howtodrawpersontestapp.domain.usecase

import com.seif.howtodrawpersontestapp.domain.repository.ChildRepository
import javax.inject.Inject

class SaveChildGradeUseCase @Inject constructor(
    private val childRepository: ChildRepository
) {
    suspend operator fun invoke(
        childId: String,
        totalGrade: String,
        mindAge: String,
        intelligenceGrade: String,
        intelligenceValue: String,
        gradeList: List<String>
    ) {
        return childRepository.saveChildGrades(
            childId,
            totalGrade,
            mindAge ,
            intelligenceGrade,
            intelligenceValue,
            gradeList
        )
    }
}