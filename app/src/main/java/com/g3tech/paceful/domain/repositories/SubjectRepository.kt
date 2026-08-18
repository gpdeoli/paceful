package com.g3tech.paceful.domain.repositories

import com.g3tech.paceful.domain.model.input.subject.Subject
import com.g3tech.paceful.domain.model.input.subject.SubjectToSelect

interface SubjectRepository {
    suspend fun registerSubject(subject: Subject): Result<Unit>
    suspend fun getSubjectsToSelect(): Result<List<SubjectToSelect>>
}