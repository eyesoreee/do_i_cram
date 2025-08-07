package com.example.doicram.db.repo

import com.example.doicram.db.entities.GradeCategories

interface GradeCategoriesRepository {
    suspend fun addCategory(category: GradeCategories): Long
    suspend fun addCategories(categories: List<GradeCategories>): List<Long>
    suspend fun updateCategory(category: GradeCategories)
    suspend fun getCategoriesForCourse(courseId: Int): List<GradeCategories>
    suspend fun getCategoryById(categoryId: Int): GradeCategories?
    suspend fun deleteCategory(category: GradeCategories)
    suspend fun deleteCategories(categories: List<GradeCategories>)
}