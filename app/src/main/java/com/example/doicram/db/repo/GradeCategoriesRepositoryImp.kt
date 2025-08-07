package com.example.doicram.db.repo

import com.example.doicram.db.dao.GradeCategoriesDao
import com.example.doicram.db.entities.GradeCategories
import javax.inject.Inject

class GradeCategoriesRepositoryImpl @Inject constructor(
    private val gradeCategoriesDao: GradeCategoriesDao
) : GradeCategoriesRepository {
    override suspend fun addCategory(category: GradeCategories): Long {
        return gradeCategoriesDao.addCategory(category)
    }

    override suspend fun addCategories(categories: List<GradeCategories>): List<Long> {
        return gradeCategoriesDao.addCategories(categories)
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

    override suspend fun deleteCategories(categories: List<GradeCategories>) {
        gradeCategoriesDao.deleteCategories(categories)
    }
}