package com.seif.howtodrawpersontestapp.domain.usecase

import com.seif.howtodrawpersontestapp.domain.repository.ChildRepository
import javax.inject.Inject

class SaveFinishingTestStateUseCase @Inject constructor(
    private val childRepository: ChildRepository
) {
    operator fun invoke(completed: Boolean) {
        return childRepository.saveFinishingTestState(completed)
    }
}