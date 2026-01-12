package com.g3tech.paceful.di

import androidx.navigation3.runtime.NavKey
import androidx.room.Room
import com.g3tech.paceful.data.StudyRepositoryImpl
import com.g3tech.paceful.data.SubjectRepositoryImpl
import com.g3tech.paceful.data.db.AppDatabase
import com.g3tech.paceful.data.db.callbacks.PrepopulateStudyStatusCallback
import com.g3tech.paceful.data.db.daos.SubjectDao
import com.g3tech.paceful.domain.repositories.StudyRepository
import com.g3tech.paceful.domain.repositories.SubjectRepository
import com.g3tech.paceful.ui.home.HomeViewModel
import com.g3tech.paceful.ui.navigation.Navigator
import com.g3tech.paceful.ui.navigation.NavigatorImpl
import com.g3tech.paceful.ui.study.createstudy.CreateStudyViewModel
import com.g3tech.paceful.ui.subject.CreateSubjectViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> {
        val callback =
            PrepopulateStudyStatusCallback(studyStatusDaoProvider = { get<AppDatabase>().studyStatusDao() })
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "paceful_database"
        ).addCallback(callback).build()
    }

    single<Navigator<NavKey>> { NavigatorImpl() }

    single<SubjectDao> {
        get<AppDatabase>().subjectDao()
    }
    single<StudyRepository> {
        StudyRepositoryImpl(get<AppDatabase>().studyDao(), get<SubjectDao>())
    }
    single<SubjectRepository> {
        SubjectRepositoryImpl(get<SubjectDao>())
    }

    viewModelOf(::HomeViewModel)
    viewModelOf(::CreateSubjectViewModel)
    viewModelOf(::CreateStudyViewModel)
}