package com.example.doicram

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.doicram.courses.SortOption

data class SortOptionItem(
    val option: SortOption,
    val label: String,
    val icon: ImageVector
)

@Composable
fun CourseFilterSortBar(
    modifier: Modifier = Modifier,
    showArchived: Boolean,
    sortOption: SortOption,
    onShowArchivedChanged: (Boolean) -> Unit,
    onSortOptionChanged: (SortOption) -> Unit
) {
    var showSortMenu by remember { mutableStateOf(false) }

    val sortOptions = listOf(
        SortOptionItem(SortOption.NAME_ASC, "Name (A-Z)", Icons.Default.ArrowUpward),
        SortOptionItem(SortOption.NAME_DESC, "Name (Z-A)", Icons.Default.ArrowDownward),
        SortOptionItem(
            SortOption.DATE_ADDED_ASC,
            "Date Added (Old First)",
            Icons.Default.ArrowUpward
        ),
        SortOptionItem(
            SortOption.DATE_ADDED_DESC,
            "Date Added (New First)",
            Icons.Default.ArrowDownward
        ),
        SortOptionItem(
            SortOption.ASSIGNMENTS_COUNT_ASC,
            "Assignments (Low-High)",
            Icons.Default.ArrowUpward
        ),
        SortOptionItem(
            SortOption.ASSIGNMENTS_COUNT_DESC,
            "Assignments (High-Low)",
            Icons.Default.ArrowDownward
        ),
        SortOptionItem(SortOption.UNITS_ASC, "Units (Low-High)", Icons.Default.ArrowUpward),
        SortOptionItem(SortOption.UNITS_DESC, "Units (High-Low)", Icons.Default.ArrowDownward),
        SortOptionItem(SortOption.GRADE_ASC, "Grade (Low-High)", Icons.Default.ArrowUpward),
        SortOptionItem(SortOption.GRADE_DESC, "Grade (High-Low)", Icons.Default.ArrowDownward)
    )

    val currentSortLabel = sortOptions.find { it.option == sortOption }?.label ?: "Name (A-Z)"

    Column(modifier = modifier.fillMaxWidth()) {
        // Filter and Sort Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Archive Toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = if (showArchived) Icons.Default.Archive else Icons.Default.Unarchive,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Show Archived",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = showArchived,
                    onCheckedChange = onShowArchivedChanged
                )
            }

            // Sort Dropdown
            Box(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { showSortMenu = true }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = currentSortLabel,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }

                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                            RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    sortOptions.forEach { sortOptionItem ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = sortOptionItem.icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = if (sortOption == sortOptionItem.option)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = sortOptionItem.label,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (sortOption == sortOptionItem.option)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface,
                                        fontWeight = if (sortOption == sortOptionItem.option)
                                            FontWeight.Medium else FontWeight.Normal
                                    )
                                }
                            },
                            onClick = {
                                onSortOptionChanged(sortOptionItem.option)
                                showSortMenu = false
                            },
                            modifier = Modifier.background(
                                if (sortOption == sortOptionItem.option)
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                                else MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

