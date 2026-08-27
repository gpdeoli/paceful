package com.g3tech.paceful.ui.study.studies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.input.subject.SubjectToSelect
import com.g3tech.paceful.ui.shared.OutlinedDatePicker
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StudiesFilterSheet(
    state: StudiesState,
    sheetState: SheetState,
    onEvent: (StudiesEvent) -> Unit,
    onDismiss: () -> Unit,
) {
    var draftSearchQuery by rememberSaveable { mutableStateOf(state.searchQueryFilter) }
    var draftStatuses by rememberSaveable { mutableStateOf(state.studyStatusFilter) }
    var draftSubjectId by rememberSaveable { mutableStateOf(state.subjectIdFilter) }
    var draftStartDate by rememberSaveable { mutableStateOf(state.startDateFilter) }
    var draftEndDate by rememberSaveable { mutableStateOf(state.endDateFilter) }
    var subjectDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    val selectedSubject = state.subjectOptions.find { it.id == draftSubjectId }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.filters),
                style = MaterialTheme.typography.titleLarge,
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = draftSearchQuery,
                onValueChange = { draftSearchQuery = it },
                label = { Text(stringResource(R.string.search)) },
                singleLine = true,
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(R.string.status),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StudyStatus.entries.forEach { status ->
                        FilterChip(
                            selected = status in draftStatuses,
                            onClick = {
                                draftStatuses = if (status in draftStatuses)
                                    draftStatuses - status
                                else
                                    draftStatuses + status
                            },
                            label = {
                                Text(
                                    when (status) {
                                        StudyStatus.PENDING -> stringResource(R.string.pending_title)
                                        StudyStatus.IN_PROGRESS -> stringResource(R.string.in_progress_title)
                                        StudyStatus.SCHEDULED -> stringResource(R.string.scheduled_title)
                                        StudyStatus.DONE -> stringResource(R.string.done_title)
                                    }
                                )
                            },
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = subjectDropdownExpanded,
                onExpandedChange = { subjectDropdownExpanded = it },
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth(),
                    value = selectedSubject?.name ?: stringResource(R.string.no_subject_selected),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.subject)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectDropdownExpanded) },
                )

                ExposedDropdownMenu(
                    expanded = subjectDropdownExpanded,
                    onDismissRequest = { subjectDropdownExpanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                if (state.subjectOptions.isEmpty()) stringResource(R.string.no_subjects_registered)
                                else stringResource(R.string.no_subject)
                            )
                        },
                        onClick = {
                            draftSubjectId = null
                            subjectDropdownExpanded = false
                        },
                    )
                    state.subjectOptions.forEach { subject ->
                        SubjectDropdownItem(
                            subject = subject,
                            onClick = {
                                draftSubjectId = subject.id
                                subjectDropdownExpanded = false
                            },
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(R.string.date_range),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                OutlinedDatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    value = draftStartDate,
                    label = stringResource(R.string.start_date),
                    allowAnyDate = true,
                    onSelectedDate = { draftStartDate = it },
                )
                OutlinedDatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    value = draftEndDate,
                    label = stringResource(R.string.end_date),
                    allowAnyDate = true,
                    onSelectedDate = { draftEndDate = it },
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        draftSearchQuery = ""
                        draftStatuses = emptySet()
                        draftSubjectId = null
                        draftStartDate = null
                        draftEndDate = null
                    },
                ) {
                    Text(stringResource(R.string.reset))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onEvent(
                            StudiesEvent.ApplyFilters(
                                searchQuery = draftSearchQuery,
                                statuses = draftStatuses,
                                subjectId = draftSubjectId,
                                startDate = draftStartDate,
                                endDate = draftEndDate,
                            )
                        )
                    },
                ) {
                    Text(stringResource(R.string.apply_filters))
                }
            }
        }
    }
}

@Composable
private fun SubjectDropdownItem(subject: SubjectToSelect, onClick: () -> Unit) {
    val contentColor = if (subject.isDueDateNear) MaterialTheme.colorScheme.error
                       else MaterialTheme.colorScheme.onSurface
    DropdownMenuItem(
        text = {
            Column {
                Text(text = subject.name, color = contentColor, style = MaterialTheme.typography.bodyLarge)
                if (subject.deadline != null) {
                    Text(text = subject.deadline, color = contentColor, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        onClick = onClick,
    )
}
