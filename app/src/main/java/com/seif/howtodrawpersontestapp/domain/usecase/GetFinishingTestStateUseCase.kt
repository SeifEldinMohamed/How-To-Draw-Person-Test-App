package com.seif.howtodrawpersontestapp.domain.usecase

import com.seif.howtodrawpersontestapp.domain.repository.ChildRepository
import javax.inject.Inject

class GetFinishingTestStateUseCase @Inject constructor(
    private val childRepository: ChildRepository
) {
    operator fun invoke():Boolean {
        return childRepository.getFinishingTestState()
    }
}