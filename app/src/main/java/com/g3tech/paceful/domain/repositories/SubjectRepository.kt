package com.g3tech.paceful.domain.repositories

import com.g3tech.paceful.domain.model.Subject

interface SubjectRepository {
    suspend fun registerSubject(subject: Subject): Result<Unit>
}