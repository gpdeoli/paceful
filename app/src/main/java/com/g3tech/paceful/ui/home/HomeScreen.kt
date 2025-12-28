package com.g3tech.paceful.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.home.components.DashboardStats
import com.g3tech.paceful.ui.home.components.Greeting
import com.g3tech.paceful.ui.home.components.StatsValues
import com.g3tech.paceful.ui.home.components.StudySection
import com.g3tech.paceful.ui.shared.BottomAppBar
import com.g3tech.paceful.ui.shared.FabItem
import com.g3tech.paceful.ui.shared.FabMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(state: HomeScreenState, onEvent: (HomeScreenEvent) -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            val fabMenuItems = listOf(
                FabItem(
                    icon = R.drawable.subject_24,
                    label = stringResource(R.string.new_subject),
                    onClick = {onEvent(HomeScreenEvent.NewSubjectClick)}
                ),
                FabItem(
                    icon = R.drawable.study_24,
                    label = stringResource(R.string.new_study),
                    onClick = {onEvent(HomeScreenEvent.NewStudyClick)}
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
                    title = stringResource(R.string.urgent_section_title),
                    description = stringResource(R.string.urgent_section_description),
                    studies = state.summaryData.urgentStudies,
                    onViewAllClick = {onEvent(HomeScreenEvent.ViewAllClick(null ))}
                )
            }
            item {
                StudySection(
                    title = stringResource(R.string.overdue_section_title),
                    description = stringResource(R.string.overdue_section_description),
                    studies = state.summaryData.overdueStudies,
                    onViewAllClick = {onEvent(HomeScreenEvent.ViewAllClick(null))}
                )
            }

            val studiesWithSubjects = state.summaryData.studiesWithSubjects
            items(state.summaryData.subjects) { subject ->
                StudySection(
                    title = subject.name,
                    description = subject.description ?: "",
                    studies = studiesWithSubjects.filter { it.subject == subject.id },
                    onViewAllClick = {})
            }
        }
    }
}
