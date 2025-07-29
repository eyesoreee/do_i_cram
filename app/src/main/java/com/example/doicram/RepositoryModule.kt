package com.example.doicram

import com.example.doicram.courses.db.dao.CoursesDao
import com.example.doicram.courses.db.dao.GradeCategoriesDao
import com.example.doicram.courses.db.repo.CoursesRepository
import com.example.doicram.courses.db.repo.CoursesRepositoryImpl
import com.example.doicram.courses.db.repo.GradeCategoriesRepository
import com.example.doicram.courses.db.repo.GradeCategoriesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoursesRepository(coursesDao: CoursesDao): CoursesRepository {
        return CoursesRepositoryImpl(coursesDao)
    }

    @Provides
    @Singleton
    fun provideGradeCategoriesRepository(gradeCategoriesDao: GradeCategoriesDao): GradeCategoriesRepository {
        return GradeCategoriesRepositoryImpl(gradeCategoriesDao)
    }

}