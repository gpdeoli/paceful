package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.ui.theme.AppThemeExtension


@Composable
fun StudyStatusIndicator(status: StudyStatus) {
    val statusText = when (status) {
        StudyStatus.PENDING -> stringResource(R.string.pending_title)
        StudyStatus.IN_PROGRESS -> stringResource(R.string.in_progress_title)
        StudyStatus.DONE -> stringResource(R.string.done_title)
        StudyStatus.SCHEDULED -> stringResource(R.string.scheduled_title)
    }
    val iconRes = when (status) {
        StudyStatus.IN_PROGRESS -> R.drawable.schedule_24
        StudyStatus.PENDING -> R.drawable.pending_24
        StudyStatus.SCHEDULED -> R.drawable.calendar_today_24
        StudyStatus.DONE -> R.drawable.check_24
    }
    val color = AppThemeExtension.colors.run {
        when (status) {
            StudyStatus.IN_PROGRESS -> inProgress.content
            StudyStatus.PENDING -> pending.content
            StudyStatus.SCHEDULED -> scheduled.content
            StudyStatus.DONE -> done.content
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp),
        )
        Spacer(Modifier.size(4.dp))
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelSmall,
            color = color,
        )
    }
}