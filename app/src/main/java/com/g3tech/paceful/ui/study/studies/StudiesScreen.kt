package com.g3tech.paceful.ui.study.studies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.ui.study.studies.components.FilterPill
import com.g3tech.paceful.ui.study.studies.components.NoStudiesContent
import com.g3tech.paceful.ui.study.studies.components.StudiesSearchBar
import com.g3tech.paceful.ui.study.studies.components.StudyListCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StudiesScreen(
    state: StudiesState,
    onEvent: (StudiesEvent) -> Unit,
) {
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val total = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false
            total > 1 && lastVisible >= (total * 0.8f).toInt()
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onEvent(StudiesEvent.LoadMore)
    }

    if (state.isFilterSheetVisible) {
        StudiesFilterSheet(
            state = state,
            sheetState = sheetState,
            onEvent = onEvent,
            onDismiss = { onEvent(StudiesEvent.ToggleFilterSheet) },
        )
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.studies)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                ),
                windowInsets = WindowInsets(0, 0, 0, 0),
                actions = {
                    IconButton(onClick = { onEvent(StudiesEvent.ToggleFilterSheet) }) {
                        BadgedBox(
                            badge = {
                                if (state.hasActiveFilters) Badge()
                            }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.tune_24),
                                contentDescription = stringResource(R.string.filters),
                            )
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(StudiesEvent.NewStudyClick) },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_24),
                    contentDescription = stringResource(R.string.new_study),
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp),
        ) {
            item {
                if (state.isLoading) {
                    LinearWavyProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                StudiesSearchBar(
                    query = state.searchQueryFilter,
                    onQueryChange = { onEvent(StudiesEvent.SearchQueryChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                )

                val filterOptions: List<StudyStatus?> = listOf(null) + StudyStatus.entries
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(filterOptions) { status ->
                        FilterPill(
                            label = when (status) {
                                null -> stringResource(R.string.all)
                                StudyStatus.PENDING -> stringResource(R.string.pending_title)
                                StudyStatus.IN_PROGRESS -> stringResource(R.string.in_progress_title)
                                StudyStatus.SCHEDULED -> stringResource(R.string.scheduled_title)
                                StudyStatus.DONE -> stringResource(R.string.done_title)
                            },
                            selected = when (status) {
                                null -> state.studyStatusFilter.isEmpty()
                                else -> status in state.studyStatusFilter
                            },
                            onClick = { onEvent(StudiesEvent.StatusToggled(status)) },
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
            }

            if (state.studies.isEmpty() && !state.isLoading) {
                item {
                    NoStudiesContent(
                        hasActiveFilter = state.hasActiveFilters,
                        onEvent = onEvent,
                        modifier = Modifier
                            .fillParentMaxHeight(0.8f)
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                    )
                }
            } else {
                items(
                    items = state.studies,
                    key = { summary -> summary.study.name + summary.study.deadline.toString() },
                ) { summary ->
                    StudyListCard(
                        studySummary = summary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    )
                }

                if (state.isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
