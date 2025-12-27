package com.g3tech.paceful.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.Study
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.Topic
import com.g3tech.paceful.ui.home.components.DashboardStats
import com.g3tech.paceful.ui.home.components.Greeting
import com.g3tech.paceful.ui.home.components.StatsValues
import com.g3tech.paceful.ui.home.components.StudySection
import com.g3tech.paceful.ui.shared.BottomAppBar
import com.g3tech.paceful.ui.shared.FabItem
import com.g3tech.paceful.ui.shared.FabMenu
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

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            val fabMenuItems = listOf(
                FabItem(
                    icon = R.drawable.subject_24,
                    label = stringResource(R.string.new_subject),
                    onClick = {}
                ),
                FabItem(
                    icon = R.drawable.study_24,
                    label = stringResource(R.string.new_study),
                    onClick = {}
                )
            )
            FabMenu(items = fabMenuItems)
        },
        topBar = {
            TopAppBar(
                title = { Greeting() },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                DashboardStats(
                    modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
                    values = StatsValues(
                        inProgressValue = 10,
                        scheduledValue = 3,
                        pendingValue = 4,
                        doneValue = 5
                    )
                )
            }

            item {
                StudySection(
                    title = "Matemática",
                    description = "Lorem ipsum dolor sit amet consectetur adipiscing elit.",
                    studies = pendingStudies,
                    onViewAllClick = {}
                )
            }
            item {
                StudySection(
                    title = "In Progress",
                    description = "Opa eai",
                    studies = inProgressStudies,
                    onViewAllClick = {}
                )
            }
            item {
                StudySection(
                    title = "Scheduled",
                    description = "Opa eai",
                    studies = scheduledStudies,
                    onViewAllClick = {}
                )
            }
            item {
                StudySection(
                    title = "Done",
                    description = "Opa eai",
                    studies = doneStudies,
                    onViewAllClick = {}
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "en",
)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}
