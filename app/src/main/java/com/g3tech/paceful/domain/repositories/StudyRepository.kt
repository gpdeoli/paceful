package com.g3tech.paceful.domain.repositories

import com.g3tech.paceful.domain.model.Study

interface StudyRepository {
    suspend fun registerStudy(study: Study)
}