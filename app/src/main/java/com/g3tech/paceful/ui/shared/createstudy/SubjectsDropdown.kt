package com.g3tech.paceful.ui.shared.createstudy

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.PartialSubject
import com.g3tech.paceful.ui.home.CreateStudyEvent
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsDropdown(
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedSubject: PartialSubject?,
    subjects: List<PartialSubject>
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
    ) {
        val hintText = selectedSubject?.name
            ?: stringResource(R.string.no_subject_selected)

        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(
                    ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    enabled = true
                )
                .fillMaxWidth(),
            value = hintText,
            onValueChange = {},
            readOnly = true,
            label = { Text("${stringResource(R.string.subject)} (${stringResource(R.string.optional)})") },
            placeholder = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            val nullOptionText = selectedSubject?.name
                ?: if (subjects.isEmpty()) stringResource(R.string.no_subjects_registered) else stringResource(
                    R.string.no_subject
                )
            DropdownMenuItem(
                text = { Text(nullOptionText) },
                onClick = {
                    CreateStudyEvent.SubjectChanged(null)
                    onExpandedChange(false)
                }
            )

            subjects.forEach { subject ->
                DropdownMenuItem(
                    text = { Text(text = subject.name) },
                    onClick = {
                        CreateStudyEvent.SubjectChanged(subject)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
