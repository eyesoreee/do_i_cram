package com.example.doicram.courses.db.repo

import com.example.doicram.courses.db.entities.GradeCategories

interface GradeCategoriesRepository {
    suspend fun addCategory(category: GradeCategories)
    suspend fun updateCategory(category: GradeCategories)
    suspend fun getCategoriesForCourse(courseId: Int): List<GradeCategories>
    suspend fun getCategoryById(categoryId: Int): GradeCategories?
    suspend fun deleteCategory(category: GradeCategories)
}