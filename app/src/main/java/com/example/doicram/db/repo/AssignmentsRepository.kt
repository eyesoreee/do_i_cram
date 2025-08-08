package com.example.doicram.db.repo

import com.example.doicram.dashboard.PendingAssignmentsInfo
import com.example.doicram.db.entities.Assignments
import kotlinx.coroutines.flow.Flow

interface AssignmentsRepository {
    suspend fun addAssignment(assignment: Assignments): Long
    suspend fun updateAssignment(assignment: Assignments)
    suspend fun getAssignmentsForCategory(categoryId: Int): List<Assignments>
    suspend fun getAssignmentById(assignmentId: Int): Assignments?
    suspend fun deleteAssignment(assignment: Assignments)
    suspend fun deleteAssignmentsForCategory(categoryId: Int)
    suspend fun getPendingAssignmentsInfo(): PendingAssignmentsInfo
    fun getPendingAssignmentsInfoFlow(): Flow<PendingAssignmentsInfo>
}