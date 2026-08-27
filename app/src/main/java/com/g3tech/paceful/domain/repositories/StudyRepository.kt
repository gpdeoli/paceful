package com.g3tech.paceful.domain.repositories

import com.g3tech.paceful.domain.model.StudiesSummary
import com.g3tech.paceful.domain.model.StudySummary
import com.g3tech.paceful.domain.model.input.CreateStudy
import com.g3tech.paceful.domain.model.input.GetStudiesQueryParams

interface StudyRepository {
    suspend fun registerStudy(study: CreateStudy): Result<Unit>
    suspend fun getStudiesSummary(): Result<StudiesSummary>
    suspend fun getStudies(params: GetStudiesQueryParams): Result<List<StudySummary>>
}