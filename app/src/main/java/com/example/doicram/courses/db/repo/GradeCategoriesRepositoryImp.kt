package com.example.doicram.courses.db.repo

import com.example.doicram.courses.db.dao.GradeCategoriesDao
import com.example.doicram.courses.db.entities.GradeCategories
import javax.inject.Inject

class GradeCategoriesRepositoryImpl @Inject constructor(
    private val gradeCategoriesDao: GradeCategoriesDao
) : GradeCategoriesRepository {

    override suspend fun addCategory(category: GradeCategories) {
        gradeCategoriesDao.addCategory(category)
    }

    override suspend fun updateCategory(category: GradeCategories) {
        gradeCategoriesDao.updateCategory(category)
    }

    override suspend fun getCategoriesForCourse(courseId: Int): List<GradeCategories> {
        return gradeCategoriesDao.getCategoriesForCourse(courseId)
    }

    override suspend fun getCategoryById(categoryId: Int): GradeCategories? {
        return gradeCategoriesDao.getCategoryById(categoryId)
    }

    override suspend fun deleteCategory(category: GradeCategories) {
        gradeCategoriesDao.deleteCategory(category)
    }
}