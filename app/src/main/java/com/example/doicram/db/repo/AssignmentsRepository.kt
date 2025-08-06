package com.example.doicram.db.repo

import com.example.doicram.db.entities.Assignments

interface AssignmentsRepository {
    suspend fun addAssignment(assignment: Assignments): Long
    suspend fun updateAssignment(assignment: Assignments)
    suspend fun getAssignmentsForCategory(categoryId: Int): List<Assignments>
    suspend fun getAssignmentById(assignmentId: Int): Assignments?
    suspend fun deleteAssignment(assignment: Assignments)
    suspend fun deleteAssignmentsForCategory(categoryId: Int)
}