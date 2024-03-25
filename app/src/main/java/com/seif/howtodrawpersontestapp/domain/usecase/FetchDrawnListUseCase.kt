package com.seif.howtodrawpersontestapp.domain.usecase

import com.seif.howtodrawpersontestapp.data.model.ChildDataModel
import com.seif.howtodrawpersontestapp.domain.repository.ChildRepository
import javax.inject.Inject

class FetchDrawnListUseCase @Inject constructor(
    private val childRepository: ChildRepository
) {
    suspend operator fun invoke(): List<ChildDataModel>? {
        return childRepository.fetchDrawnList()
    }
}