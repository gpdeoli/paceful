package com.g3tech.paceful.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.g3tech.paceful.domain.model.StudiesSummary
import com.g3tech.paceful.domain.model.StudiesSummaryNumbers
import com.g3tech.paceful.domain.model.Study
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.StudySummary
import com.g3tech.paceful.domain.model.input.subject.PartialSubject
import com.g3tech.paceful.ui.home.components.DashboardStats
import com.g3tech.paceful.ui.home.components.Greeting
import com.g3tech.paceful.ui.home.components.NoStudies
import com.g3tech.paceful.ui.home.components.StatsValues
import com.g3tech.paceful.ui.home.components.StudySection
import com.g3tech.paceful.ui.shared.bottomappbar.BottomAppBar
import com.g3tech.paceful.ui.shared.FabItem
import com.g3tech.paceful.ui.shared.FabMenu
import com.g3tech.paceful.ui.theme.AppTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(state: HomeScreenState, onEvent: (HomeScreenEvent) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        contentWindowInsets = WindowInsets(left = 0, right = 0, top = 0, bottom = 0),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            val fabMenuItems = listOf(
                FabItem(
                    icon = R.drawable.subject_24,
                    label = stringResource(R.string.new_subject),
                    onClick = { onEvent(HomeScreenEvent.NewSubjectClick) }
                ),
                FabItem(
                    icon = R.drawable.study_24,
                    label = stringResource(R.string.new_study),
                    onClick = { onEvent(HomeScreenEvent.NewStudyClick) }
                )
            )
            FabMenu(items = fabMenuItems)
        },
        topBar = {
            TopAppBar(
                title = { Greeting() },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                ),
            )
        },
    ) { innerPadding ->
        val summaryData = state.summaryData
        val studiesEmpty =
            summaryData.studiesSummaryNumbers.none { studiesSummaryNumbers -> studiesSummaryNumbers.value != 0 }
        val subjectsEmpty = summaryData.subjects.isEmpty()

        if (state.isLoading) {
            LinearWavyProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            )
            return@Scaffold
        }

        if (studiesEmpty) {
            NoStudies(
                innerPadding = innerPadding,
                areThereSubjects = !subjectsEmpty,
                onEvent = onEvent
            )
            return@Scaffold
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                val summaryNumbers = state.summaryData.studiesSummaryNumbers
                val inProgressStudies =
                    summaryNumbers.find { it.status == StudyStatus.IN_PROGRESS }?.value
                val scheduledStudies =
                    summaryNumbers.find { it.status == StudyStatus.SCHEDULED }?.value
                val pendingStudies = summaryNumbers.find { it.status == StudyStatus.PENDING }?.value
                val doneStudies = summaryNumbers.find { it.status == StudyStatus.DONE }?.value
                DashboardStats(
                    modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
                    values = StatsValues(
                        inProgressValue = inProgressStudies ?: 0,
                        scheduledValue = scheduledStudies ?: 0,
                        pendingValue = pendingStudies ?: 0,
                        doneValue = doneStudies ?: 0
                    )
                )
            }

            if (state.summaryData.urgentStudies.isNotEmpty()) {
                item {
                    StudySection(
                        title = stringResource(R.string.urgent_section_title),
                        studies = state.summaryData.urgentStudies,
                        onViewAllClick = { onEvent(HomeScreenEvent.ViewAllClick(null)) }
                    )
                }
            }

            if (state.summaryData.overdueStudies.isNotEmpty()) {
                item {
                    StudySection(
                        title = stringResource(R.string.overdue_section_title),
                        studies = state.summaryData.overdueStudies,
                        onViewAllClick = { onEvent(HomeScreenEvent.ViewAllClick(null)) }
                    )
                }
            }

            val studiesWithSubjects = state.summaryData.studiesWithSubjects
            items(state.summaryData.subjects) { subject ->
                StudySection(
                    title = subject.name,
                    studies = studiesWithSubjects.filter { it.subject?.id == subject.id },
                    onViewAllClick = {},
                )
            }

            val showWithoutSubjectStudies =
                summaryData.studiesWithSubjects.isNotEmpty() && !summaryData.studiesWithoutSubjects.isNullOrEmpty()
            if (showWithoutSubjectStudies) {
                item {
                    StudySection(
                        title = stringResource(R.string.studies_without_subjects_section_title),
                        studies = summaryData.studiesWithoutSubjects,
                        onViewAllClick = {},
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenEmptyPreview() {
    AppTheme {
        HomeScreen(state = HomeScreenState(), onEvent = {})
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "pt"
)
@Composable
fun HomeScreenWithDataPreview() {
    val urgentStudies = List(3) {
        StudySummary(
            study = Study(
                name = "Urgent Study ${it + 1}",
                status = StudyStatus.IN_PROGRESS,
                deadline = LocalDate.now()
            ),
            topics = null,
            subject = PartialSubject(id = 1, name = "Fisica", description = "Oi")
        )
    }

    val overdueStudies = List(3) {
        StudySummary(
            study = Study(
                name = "Overdue Study ${it + 1}",
                status = StudyStatus.IN_PROGRESS,
                deadline = LocalDate.now().minusDays(1)
            ),
        )
    }

    val summaryNumbers = listOf(
        StudiesSummaryNumbers(value = 5, status = StudyStatus.PENDING),
        StudiesSummaryNumbers(value = 3, status = StudyStatus.IN_PROGRESS),
        StudiesSummaryNumbers(value = 2, status = StudyStatus.SCHEDULED),
        StudiesSummaryNumbers(value = 10, status = StudyStatus.DONE)
    )

    val state = HomeScreenState(
        summaryData = StudiesSummary(
            studiesSummaryNumbers = summaryNumbers,
            subjects = emptyList(),
            urgentStudies = urgentStudies,
            overdueStudies = overdueStudies,
            studiesWithSubjects = emptyList(),
            studiesWithoutSubjects = urgentStudies
        )
    )

    AppTheme {
        HomeScreen(state = state, onEvent = {})
    }
}
