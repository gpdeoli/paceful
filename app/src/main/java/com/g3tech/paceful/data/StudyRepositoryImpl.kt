package com.g3tech.paceful.data

import com.g3tech.paceful.data.db.daos.StudyDao
import com.g3tech.paceful.data.db.daos.SubjectDao
import com.g3tech.paceful.data.db.entities.toDbEntity
import com.g3tech.paceful.domain.model.StudiesSummary
import com.g3tech.paceful.domain.model.input.CreateStudy
import com.g3tech.paceful.domain.repositories.StudyRepository

class StudyRepositoryImpl(
    private val studyDao: StudyDao,
    private val subjectDao: SubjectDao
) : StudyRepository {

    override suspend fun registerStudy(study: CreateStudy): Result<Unit> {
        val formattedStudy = study.toDbEntity()
        val formattedTopics = study.topics?.map { it.toDbEntity() }

        try {
            if (!formattedTopics.isNullOrEmpty()) {
                studyDao.insertStudyWithTopics(formattedStudy, formattedTopics)
            } else {
                studyDao.insertStudy(formattedStudy)
            }
            return Result.success(Unit)
        } catch (err: Exception) {
            return Result.failure(err)
        }

    }

    override suspend fun getStudiesSummary(): Result<StudiesSummary> {
        try {
            val summarySubjects = subjectDao.getSummarySubjects()

            val subjectsIds = summarySubjects.map { it.id }
            val urgentStudies = studyDao.getUrgentStudies()
            val overdueStudies = studyDao.getOverdueStudies()
            val studiesWithSubject = studyDao.getStudiesWithSubjects(subjectsIds = subjectsIds)
            val studiesSummaryNumbers = studyDao.getStudiesSummaryNumbers()

            val studiesWithoutSubjects = if (studiesWithSubject.isEmpty()) {
                studyDao.getStudiesWithoutSubjects()
            } else null

            val studiesSummary = StudiesSummary(
                subjects = summarySubjects,
                urgentStudies = urgentStudies.map { it.toModel() },
                overdueStudies = overdueStudies.map { it.toModel() },
                studiesWithSubjects = studiesWithSubject.map { it.toModel() },
                studiesSummaryNumbers = studiesSummaryNumbers.map { it.toModel() },
                studiesWithoutSubjects = studiesWithoutSubjects?.map { it.toModel() }
            )
            return Result.success(studiesSummary)
        } catch (err: Exception) {
            return Result.failure(err)
        }
    }
}