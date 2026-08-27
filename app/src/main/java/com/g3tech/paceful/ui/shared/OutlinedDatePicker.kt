package com.g3tech.paceful.ui.shared

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.utils.getLocalizedDateFormatter
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedDatePicker(
    modifier: Modifier = Modifier,
    value: LocalDate?,
    label: String,
    pickerTitle: String = stringResource(R.string.select_date),
    allowAnyDate: Boolean = false,
    onSelectedDate: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = getLocalizedDateFormatter()
    val formattedValue = if (value?.isEqual(LocalDate.now()) ?: false) {
        stringResource(R.string.today)
    } else if (value?.isEqual(LocalDate.now().plusDays(1)) ?: false) {
        stringResource(R.string.tomorrow)
    } else {
        value?.format(dateFormatter) ?: ""
    }

    OutlinedTextField(
        modifier = modifier,
        value = formattedValue,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.date_range_24),
                    contentDescription = "select date"
                )
            }
        },
        interactionSource = remember { MutableInteractionSource() }.also { interactionSource ->
            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect {
                    if (it is PressInteraction.Release) {
                        showDatePicker = true
                    }
                }
            }
        }
    )

    if (showDatePicker) {
        val selectableDates = if (allowAnyDate) {
            object : SelectableDates {}
        } else {
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val todayUtc = LocalDate.now().atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli()
                    return utcTimeMillis >= todayUtc
                }
            }
        }
        val datePickerState = rememberDatePickerState(
            initialSelectedDate = value,
            selectableDates = selectableDates
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedDate = datePickerState.getSelectedDate()
                    selectedDate?.let { date -> onSelectedDate(date) }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState, title = {
                Text(
                    text = pickerTitle,
                    modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp)
                )
            })
        }
    }
}
