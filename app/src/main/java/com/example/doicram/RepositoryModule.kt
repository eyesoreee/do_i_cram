package com.example.doicram

import com.example.doicram.db.dao.AssignmentsDao
import com.example.doicram.db.dao.CoursesDao
import com.example.doicram.db.dao.GradeCategoriesDao
import com.example.doicram.db.dao.GradeScaleDao
import com.example.doicram.db.repo.AssignmentsRepository
import com.example.doicram.db.repo.AssignmentsRepositoryImpl
import com.example.doicram.db.repo.CoursesRepository
import com.example.doicram.db.repo.CoursesRepositoryImpl
import com.example.doicram.db.repo.GradeCategoriesRepository
import com.example.doicram.db.repo.GradeCategoriesRepositoryImpl
import com.example.doicram.db.repo.GradeScaleRepository
import com.example.doicram.db.repo.GradeScaleRepositoryImpl
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
    fun provideCoursesRepository(
        coursesDao: CoursesDao,
        gradeScaleDao: GradeScaleDao
    ): CoursesRepository {
        return CoursesRepositoryImpl(coursesDao, gradeScaleDao)
    }

    @Provides
    @Singleton
    fun provideGradeCategoriesRepository(gradeCategoriesDao: GradeCategoriesDao): GradeCategoriesRepository {
        return GradeCategoriesRepositoryImpl(gradeCategoriesDao)
    }

    @Provides
    @Singleton
    fun provideAssignmentsRepository(assignmentsDao: AssignmentsDao): AssignmentsRepository {
        return AssignmentsRepositoryImpl(assignmentsDao)
    }

    @Provides
    @Singleton
    fun provideGradeScalesRepository(gradeScalesDao: GradeScaleDao): GradeScaleRepository {
        return GradeScaleRepositoryImpl(gradeScalesDao)
    }
}