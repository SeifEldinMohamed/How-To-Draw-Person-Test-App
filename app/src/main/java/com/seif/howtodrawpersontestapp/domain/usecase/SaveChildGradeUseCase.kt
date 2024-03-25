package com.seif.howtodrawpersontestapp.domain.usecase

import com.seif.howtodrawpersontestapp.domain.repository.ChildRepository
import javax.inject.Inject

class SaveChildGradeUseCase @Inject constructor(
    private val childRepository: ChildRepository
) {
    suspend operator fun invoke(
        childId: String,
        childGrade:String,
        childIntelligence:String
    ) {
        return childRepository.saveChildGrades(childId, childGrade, childIntelligence)
    }
}