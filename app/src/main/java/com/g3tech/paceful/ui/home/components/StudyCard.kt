package com.g3tech.paceful.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.Study
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.Topic
import com.g3tech.paceful.ui.theme.AppTheme
import com.g3tech.paceful.ui.utils.getLocalizedDateFormatter
import java.time.LocalDate

@Composable
fun StudyCard(
    study: Study,
    topics: List<Topic>?,
    subjectName: String? = null,
) {
    var isCardOpen by remember { mutableStateOf(false) }
    val dateFormatter = getLocalizedDateFormatter()
    val isDone = study.status == StudyStatus.DONE
    val isOverdue = !isDone && study.deadline.isBefore(LocalDate.now())

    Surface(
        modifier = Modifier
            .width(320.dp)
            .padding(vertical = 1.dp),
        shape = RoundedCornerShape(48.dp),
        color = if (isDone) MaterialTheme.colorScheme.surfaceVariant
        else MaterialTheme.colorScheme.surface,
        shadowElevation = if (isDone) 0.dp else 4.dp,
    ) {
        Box(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    if (subjectName != null) {
                        SubjectPill(subjectName)
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                    StudyStatusIndicator(study.status)
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = study.name,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                    ),
                    color = if (isDone) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.due_date).uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = study.deadline.format(dateFormatter),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                            ),
                            color = when {
                                isOverdue -> MaterialTheme.colorScheme.error
                                isDone -> MaterialTheme.colorScheme.onSurfaceVariant
                                else -> MaterialTheme.colorScheme.onBackground
                            },
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = if (isDone) Color.Transparent
                                else MaterialTheme.colorScheme.surfaceVariant,
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        IconButton(
                            // TODO
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.arrow_right_24),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(12.dp)
                            )
                        }
                    }
                }

                val isTopicsEmpty = topics.isNullOrEmpty()
                if (!isTopicsEmpty) {
                    HorizontalDivider(Modifier.padding(top = 16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = "${topics.size} ${stringResource(R.string.topics).lowercase()}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        IconButton(
                            onClick = { isCardOpen = !isCardOpen },
                            Modifier.size(24.dp))
                        {
                            Icon(
                                painter = painterResource(
                                    if (isCardOpen) R.drawable.arrow_up_24 else R.drawable.arrow_down_24
                                ),
                                contentDescription = null,
                            )
                        }
                    }

                    if (isCardOpen) {
                        val itemsCount = minOf(topics.size, 3)
                        Column {
                            for (i in 0 until itemsCount) {
                                val isThereMoreTopics = topics.size > 3 && i == 2
                                val textContent =
                                    if (isThereMoreTopics) "+${topics.size - 2}" else "• ${topics[i].name}"
                                Text(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    text = textContent,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ---------- Previews ----------
@Preview(name = "StudyCard — Light", showBackground = true)
@Composable
private fun StudyCardLightPreview() {
    AppTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            StudyCard(
                study = previewStudy(StudyStatus.DONE),
                topics = previewTopics(),
                subjectName = "Organic Chemistry"
            )
            StudyCard(
                study = previewStudy(StudyStatus.PENDING),
                topics = previewTopics(),
                subjectName = "Calculus III"
            )
            StudyCard(
                study = previewStudy(StudyStatus.DONE),
                topics = null,
                subjectName = "World History"
            )
            StudyCard(
                study = previewStudy(StudyStatus.DONE),
                topics = previewTopics(),
                subjectName = "Literature"
            )
        }
    }
}

@Preview(name = "StudyCard — Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StudyCardDarkPreview() {
    AppTheme(darkTheme = true) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
            StudyCard(
                study = previewStudy(StudyStatus.IN_PROGRESS),
                topics = previewTopics(),
                subjectName = "Organic Chemistry"
            )
            StudyCard(
                study = previewStudy(StudyStatus.PENDING),
                topics = previewTopics(),
                subjectName = "Calculus III"
            )
            StudyCard(
                study = previewStudy(StudyStatus.SCHEDULED),
                topics = null,
                subjectName = "World History"
            )
            StudyCard(
                study = previewStudy(StudyStatus.DONE),
                topics = previewTopics(),
                subjectName = "Literature"
            )
        }
    }
}

private fun previewStudy(status: StudyStatus) = Study(
    name = "Advanced Cognitive Psychology: Neural Pathways",
    status = status,
    deadline = LocalDate.of(2023, 10, 12),
)

private fun previewTopics() = listOf(
    Topic("Introduction"),
    Topic("Core Concepts"),
    Topic("Advanced Topics"),
    Topic("Review"),
)
