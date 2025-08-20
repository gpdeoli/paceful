package com.g3tech.paceful.domain

import com.g3tech.paceful.data.StudiesRepository
import com.g3tech.paceful.domain.entities.Study
import javax.inject.Inject

class StudiesUseCase @Inject constructor(
    private val studiesRepository: StudiesRepository
) {
    suspend fun registerStudy(study: Study): Result<Unit> {
        if ()

        try {
            studiesRepository.registerStudy(study)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getStudies() {
        studiesRepository.getStudies()
    }
}