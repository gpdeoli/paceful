package com.g3tech.paceful.data.db.callbacks

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.g3tech.paceful.data.db.daos.StudyStatusDao
import com.g3tech.paceful.data.db.entities.StudyStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class PrepopulateStudyStatusCallback @Inject constructor(
    private val studyStatusDaoProvider: Provider<StudyStatusDao>
) : RoomDatabase.Callback() {

    companion object {
        private val STUDY_STATUS_LIST = listOf(
            StudyStatus(id = 1, name = "PENDING"),
            StudyStatus(id = 2, name = "IN PROGRESS"),
            StudyStatus(id = 3, name = "DONE"),
            StudyStatus(id = 4, name = "SCHEDULED")
        )
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            prepopulateStudyStatuses()
        }
    }

    private fun prepopulateStudyStatuses() {
        val studyStatusDao = studyStatusDaoProvider.get()
        studyStatusDao.insertAll(STUDY_STATUS_LIST)
    }
}