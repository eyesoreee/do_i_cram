package com.example.doicram.courses

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.doicram.courses.db.entities.Courses

@Composable
fun CourseList(
    state: CoursesState,
    onEdit: () -> Unit,
    onDelete: (Courses) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(state.courses) { course ->
            CourseCard(
                course = course,
                onEdit = onEdit,
                onDelete = { onDelete(course) }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}