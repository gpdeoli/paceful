package com.g3tech.paceful.domain.repositories

import com.g3tech.paceful.domain.model.StudiesSummary
import com.g3tech.paceful.domain.model.input.CreateStudy

interface StudyRepository {
    suspend fun registerStudy(study: CreateStudy) : Result<Unit>
    suspend fun getStudiesSummary(): Result<StudiesSummary>
}