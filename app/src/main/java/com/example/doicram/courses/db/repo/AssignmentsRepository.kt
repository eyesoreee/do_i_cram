package com.example.doicram.courses.db.repo

import com.example.doicram.courses.db.entities.Assignments

interface AssignmentsRepository {
    suspend fun addAssignment(assignment: Assignments): Long
    suspend fun updateAssignment(assignment: Assignments)
    suspend fun getAssignmentsForCategory(categoryId: Int): List<Assignments>
    suspend fun getAssignmentById(assignmentId: Int): Assignments?
    suspend fun deleteAssignment(assignment: Assignments)
    suspend fun deleteAssignmentsForCategory(categoryId: Int)
}