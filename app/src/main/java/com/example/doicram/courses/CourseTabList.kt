package com.example.doicram.courses

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CourseTabList(
    courseTabs: List<CourseTab>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        item {
            CourseTabButton(
                onClick = { onTabSelected(-1) },
                isSelected = selectedTabIndex == -1,
                icon = Icons.Outlined.Settings,
                text = "Manage Courses"
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        items(courseTabs.size) { index ->
            CourseTabButton(
                onClick = { onTabSelected(index) },
                isSelected = selectedTabIndex == index,
                icon = courseTabs[index].icon,
                text = courseTabs[index].text,
                courseGrade = courseTabs[index].courseGrade
            )

            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}