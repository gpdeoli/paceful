package com.g3tech.paceful.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.Study
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.Topic
import com.g3tech.paceful.ui.home.components.Greeting
import com.g3tech.paceful.ui.home.components.StudyCard
import com.g3tech.paceful.ui.theme.AppTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val pendingStudies = listOf(
        Study(
            name = "Advanced Mathematics I",
            status = StudyStatus.PENDING,
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
            subject = null,
            deadline = LocalDate.of(2025, 12, 31)
        ),
        Study(
            subject = null,
            name = "Advanced Mathematics I",
            status = StudyStatus.PENDING,
            topics = emptyList(),
            deadline = LocalDate.now()
        )
    )
    val inProgressStudies = List(5) {
        Study(
            name = "In Progress Study ${it + 1}",
            subject = null,
            status = StudyStatus.IN_PROGRESS,
            topics = emptyList(),
            deadline = LocalDate.now()
        )
    }
    val doneStudies = List(5) {
        Study(
            name = "Done Study ${it + 1}",
            subject = null,
            status = StudyStatus.DONE,
            topics = emptyList(),
            deadline = LocalDate.of(2025, 12, 31)
        )
    }
    val scheduledStudies = List(5) {
        Study(
            name = "Scheduled Study ${it + 1}",
            subject = null,
            status = StudyStatus.SCHEDULED,
            topics = emptyList(),
            deadline = LocalDate.now()
        )
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.add_24),
                    contentDescription = stringResource(R.string.new_study)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.new_study),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Greeting() },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                StudySection(
                    title = "Pending",
                    studies = pendingStudies,
                    onViewAllClick = {}
                )
            }
            item {
                StudySection(
                    title = "In Progress",
                    studies = inProgressStudies,
                    onViewAllClick = {}
                )
            }
            item {
                StudySection(
                    title = "Scheduled",
                    studies = scheduledStudies,
                    onViewAllClick = {}
                )
            }
            item {
                StudySection(
                    title = "Done",
                    studies = doneStudies,
                    onViewAllClick = {}
                )
            }
        }
    }
}

@Composable
fun StudySection(
    title: String,
    studies: List<Study>,
    onViewAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            TextButton(onClick = onViewAllClick) {
                Text(text = "View all")
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(studies) { study ->
                StudyCard(study = study, true)
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "pt",
)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}
