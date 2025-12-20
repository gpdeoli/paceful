package com.g3tech.paceful.di

import android.content.Context
import androidx.room.Room
import com.g3tech.paceful.data.db.AppDatabase
import com.g3tech.paceful.data.db.callbacks.PrepopulateStudyStatusCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        callback: PrepopulateStudyStatusCallback
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "paceful_database")
            .addCallback(callback).build()
    }

    @Provides
    fun provideStudyDao(database: AppDatabase) = database.studyDao()

    @Provides
    fun provideStudyStatusDao(database: AppDatabase) = database.studyStatusDao()
}