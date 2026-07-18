package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.theme.AppTheme
import com.g3tech.paceful.ui.theme.AppThemeExtension

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DashboardStats(modifier: Modifier = Modifier, values: StatsValues) {
    val statusColors = AppThemeExtension.colors
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            CardStats(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.in_progress_title),
                value = values.inProgressValue,
                icon = R.drawable.play_24,
                statusColor = statusColors.inProgress,
            )
            CardStats(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.scheduled_title),
                value = values.scheduledValue,
                icon = R.drawable.calendar_today_24,
                statusColor = statusColors.scheduled,
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            CardStats(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.pending_title),
                value = values.pendingValue,
                icon = R.drawable.schedule_24,
                statusColor = statusColors.pending,
            )
            CardStats(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.done_title),
                value = values.doneValue,
                icon = R.drawable.check_24,
                statusColor = statusColors.done,
            )
        }
    }
}

data class StatsValues(
    val inProgressValue: Int,
    val scheduledValue: Int,
    val pendingValue: Int,
    val doneValue: Int
)

@Preview(locale = "pt")
@Composable
fun DashboardStatsPreview() {
    AppTheme {
        DashboardStats(values = StatsValues(2, 3, 4, 5))
    }
}
