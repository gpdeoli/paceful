package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.ui.theme.AppThemeExtension

@Composable
fun StudyStatusBadge(status: StudyStatus) {
    val statusText = when (status) {
        StudyStatus.PENDING -> stringResource(R.string.pending_title)
        StudyStatus.IN_PROGRESS -> stringResource(R.string.in_progress_title)
        StudyStatus.DONE -> stringResource(R.string.done_title)
        StudyStatus.SCHEDULED -> stringResource(R.string.scheduled_title)
    }
    val statusColor = when (status) {
        StudyStatus.PENDING -> AppThemeExtension.colors.pendingColor
        StudyStatus.IN_PROGRESS -> AppThemeExtension.colors.inProgressColor
        StudyStatus.DONE -> AppThemeExtension.colors.doneColor
        StudyStatus.SCHEDULED -> AppThemeExtension.colors.scheduledColor
    }

    Badge(
        containerColor = statusColor
    ) {
        Text(statusText, modifier = Modifier.padding(4.dp))
    }
}