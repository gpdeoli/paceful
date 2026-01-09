package com.g3tech.paceful.ui.shared.createstudy

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.home.CreateStudyEvent
import com.g3tech.paceful.ui.home.HomeScreenEvent
import com.g3tech.paceful.ui.shared.OutlinedDatePicker
import com.g3tech.paceful.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateStudyDialog(
    isOpen: Boolean,
    state: CreateStudyState,
    onEvent: (HomeScreenEvent) -> Unit
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

    if (isOpen) {
        Dialog(
            onDismissRequest = { onEvent(HomeScreenEvent.ToggleCreateStudyDialog(!isOpen)) },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(R.string.new_study)) },
                        navigationIcon = {
                            IconButton(onClick = { onEvent(HomeScreenEvent.ToggleCreateStudyDialog(!isOpen)) }) {
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

                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
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
                                    onEvent(
                                        CreateStudyEvent.DeadlineChanged(selectedDate = date)
                                    )
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        SubjectsDropdown(
                            isExpanded = expanded,
                            onExpandedChange = { isExpanded -> expanded = isExpanded },
                            selectedSubject = state.selectedSubject,
                            subjects = state.subjects
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

                        Spacer(modifier = Modifier.height(6.dp))
                        LazyColumn(contentPadding = PaddingValues(vertical = 12.dp)) {
                            items(items = state.topics ?: emptyList()) { topic ->
                                CreateTopicCard(
                                    topic = topic,
                                    onRemove = { topic -> onEvent(CreateStudyEvent.RemoveTopic(topic)) })
                                Spacer(modifier = Modifier.padding(bottom = 6.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO, locale = "pt")
@Composable
fun CreateStudyDialogPreview() {
    AppTheme {
        CreateStudyDialog(
            isOpen = true, state = CreateStudyState(
                topics = emptyList(),
                saveFailed = true,
                isLoading = true
            ),
            onEvent = {}
        )
    }
}