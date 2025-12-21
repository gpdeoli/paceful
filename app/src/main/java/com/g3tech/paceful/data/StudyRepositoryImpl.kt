package com.g3tech.paceful.data

import com.g3tech.paceful.data.db.daos.StudyDao
import com.g3tech.paceful.domain.model.Study
import com.g3tech.paceful.domain.repositories.StudyRepository


class StudyRepositoryImpl(
    private val studyDao: StudyDao
) : StudyRepository {

    override suspend fun registerStudy(study: Study) {
        val formattedStudy = study.toDbEntity()
        val formattedTopics = study.topics?.map { it.toDbEntity() }

        if (formattedTopics != null) {
            studyDao.insertStudyWithTopics(formattedStudy, formattedTopics)
        } else {
            studyDao.insertStudy(formattedStudy)
        }
    }

    suspend fun getStudies() {
        val pendingStudies = studyDao.getPendingStudies()
        val inProgressStudies = studyDao.getInProgressStudies()
        val doneStudies = studyDao.getDoneStudies()
        val scheduledStudies = studyDao.getScheduledStudies()
    }
}