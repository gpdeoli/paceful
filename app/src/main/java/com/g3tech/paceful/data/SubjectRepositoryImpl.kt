package com.g3tech.paceful.data

import com.g3tech.paceful.data.db.daos.SubjectDao
import com.g3tech.paceful.data.db.entities.toDbEntity
import com.g3tech.paceful.domain.model.Subject
import com.g3tech.paceful.domain.repositories.SubjectRepository

class SubjectRepositoryImpl(private val subjectDao: SubjectDao): SubjectRepository {

    override suspend fun registerSubject(subject: Subject): Result<Unit> {
        val subjectEntity = subject.toDbEntity()

        return try {
            Result.success(subjectDao.insertSubject(subjectEntity))
        } catch (err: Exception) {
            Result.failure(err)
        }
    }
}