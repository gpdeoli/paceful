package com.g3tech.paceful.ui.study.createstudy

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.input.CreateTopic
import com.g3tech.paceful.ui.shared.OutlinedDatePicker
import com.g3tech.paceful.ui.study.createstudy.components.CreateTopicCard
import com.g3tech.paceful.ui.study.createstudy.components.SubjectsDropdown
import com.g3tech.paceful.ui.study.createstudy.components.TopicForm
import com.g3tech.paceful.ui.theme.AppTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateStudyScreen(
    state: CreateStudyState,
    onEvent: (CreateStudyEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val saveFailedMessage = stringResource(R.string.study_save_failed)
    LaunchedEffect(state.saveFailed) {
        if (state.saveFailed) {
            scope.launch { snackbarHostState.showSnackbar(message = saveFailedMessage) }
            onEvent(CreateStudyEvent.SaveFailedMessageShown)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_study)) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(CreateStudyEvent.GoBack) }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.close_24),
                            contentDescription = "close"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { onEvent(CreateStudyEvent.Save) },
                        enabled = state.canSave
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            )
        }
    ) { innerPadding ->

        if (state.isLoading) {
            LinearWavyProgressIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.name,
                    onValueChange = { name -> onEvent(CreateStudyEvent.NameChanged(name)) },
                    label = { Text(stringResource(R.string.name)) }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedDatePicker(
                        modifier = Modifier.weight(1f),
                        value = state.startDate,
                        label = stringResource(R.string.start_date),
                        pickerTitle = stringResource(R.string.select_start_date),
                        onSelectedDate = { date ->
                            onEvent(CreateStudyEvent.StartDateChanged(selectedDate = date))
                        }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    OutlinedDatePicker(
                        modifier = Modifier.weight(1f),
                        value = state.deadline,
                        label = stringResource(R.string.deadline),
                        pickerTitle = stringResource(R.string.select_deadline),
                        onSelectedDate = { date ->
                            onEvent(CreateStudyEvent.DeadlineChanged(selectedDate = date))
                        }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                SubjectsDropdown(
                    isExpanded = expanded,
                    onExpandedChange = { isExpanded -> expanded = isExpanded },
                    selectedSubject = state.selectedSubject,
                    subjects = state.subjectOptions,
                    onEvent = onEvent
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(24.dp))

                TopicForm(
                    name = state.currentTopic.name,
                    deadline = state.currentTopic.deadline,
                    onEvent = onEvent,
                    enabled = state.canAddTopic
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    stringResource(R.string.topics),
                    style = MaterialTheme.typography.titleLarge
                )

                if (state.topics.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.chat_bubble_off_24),
                            contentDescription = stringResource(R.string.no_topics),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            stringResource(R.string.no_topics),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }

            items(
                items = state.topics ?: emptyList(),
                key = { topic -> topic.hashCode() }
            ) { topic ->
                CreateTopicCard(
                    topic = topic,
                    onRemove = { topic -> onEvent(CreateStudyEvent.RemoveTopic(topic)) }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "pt")
@Composable
fun CreateStudyDialogPreview() {
    AppTheme {
        CreateStudyScreen(
            state = CreateStudyState(
                topics = listOf(CreateTopic(name = "eba", deadline = LocalDate.now())),
                isLoading = true
            ),
            onEvent = {}
        )
    }
}