package com.example.doicram.courses.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithAssignments(
    @Embedded val category: GradeCategories,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val assignments: List<Assignments>
)