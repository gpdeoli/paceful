package com.g3tech.paceful.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.Study
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.Topic
import com.g3tech.paceful.ui.theme.AppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun StudyCard(study: Study, isCardOpen: Boolean = true) {
    val dateFormatter = getLocalizedDateFormatter()

    val today = LocalDate.now()
    val isOverdue = study.deadline.isBefore(today)
    val isDeadlineToday = study.deadline.isEqual(today)
    val dueText = "${stringResource(R.string.due)}:"
    val overdueText = "(${stringResource(R.string.overdue)})"
    val todayText = stringResource(R.string.today)

    Card(
        modifier = Modifier
            .width(350.dp)
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .padding(start = 2.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    text = study.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                )
                StudyStatusBadge(status = study.status)
            }
            Spacer(modifier = Modifier.padding(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.calendar_today_24),
                    contentDescription = "calendar icon",
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    study.startDate.format(dateFormatter),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.schedule_24),
                    contentDescription = "clock icon",
                )
                Spacer(modifier = Modifier.width(4.dp))

                val baseDueTitle = "$dueText ${study.deadline.format(dateFormatter)}"
                val dueTitle: String = if (isOverdue) {
                    "$baseDueTitle $overdueText"
                } else if (isDeadlineToday) {
                    "$baseDueTitle ($todayText)"
                } else {
                    baseDueTitle
                }
                Text(
                    dueTitle,
                    color = if (isOverdue) MaterialTheme.colorScheme.error else Color.Unspecified,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 12.dp))

            val isTopicsEmpty = study.topics.isNullOrEmpty()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                val topicsLabel =
                    if (isTopicsEmpty) stringResource(R.string.no_topics) else "${study.topics.size} ${
                        stringResource(R.string.topics).lowercase()
                    }"
                Text(
                    topicsLabel, style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))

                if (!isTopicsEmpty) {
                    val iconToShow =
                        if (isCardOpen) painterResource(R.drawable.arrow_up_24) else painterResource(
                            R.drawable.arrow_down_24
                        )
                    IconButton(onClick = {}, Modifier.size(24.dp)) {
                        Icon(painter = iconToShow, contentDescription = "arrow icon")
                    }
                }
            }

            if (isCardOpen && !isTopicsEmpty) {
                val itemsCount = if (study.topics.size > 3) 3 else study.topics.size
                Column {
                    for (i in 0 until itemsCount) {
                        val isThereMoreTopics = study.topics.size > 3 && i == 2
                        val textContent =
                            if (isThereMoreTopics) "+${study.topics.size -2}" else "• ${study.topics[i].name}"
                        Text(
                            modifier = Modifier.padding(vertical = 2.dp),
                            text = textContent,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getLocalizedDateFormatter(): DateTimeFormatter {
    val locale = LocalConfiguration.current.locales[0]

    return remember(locale) {
        if (locale.language == "pt") {
            DateTimeFormatter.ofPattern("d MMM yyyy", locale)
        } else {
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
        }
    }
}

@Preview(locale = "en")
@Composable
fun StudyCardPreview() {
    val study = Study(
        subject = null,
        name = "Advanced Mathematics",
        status = StudyStatus.IN_PROGRESS,
        topics = listOf(
            Topic(
                name = "Calculus Integration",
                deadline = LocalDate.now()
            ),
            Topic(
                name = "Calculus Integration",
                deadline = null
            ),
            Topic(
                name = "Calculus Integration",
                deadline = null
            ),
            Topic(
                name = "Calculus Integration",
                deadline = null
            )
        ),
        deadline = LocalDate.of(2025, 12, 25)
    )

    AppTheme {
        StudyCard(study)
    }
}