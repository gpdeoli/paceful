package com.g3tech.paceful.ui.subject

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.shared.OutlinedDatePicker
import com.g3tech.paceful.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateSubjectScreen(
    state: CreateSubjectState,
    onEvent: (CreateSubjectEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val saveFailedMessage = stringResource(R.string.study_save_failed)
    LaunchedEffect(state.saveFailed) {
        if (state.saveFailed) {
            scope.launch { snackbarHostState.showSnackbar(message = saveFailedMessage) }
            onEvent(CreateSubjectEvent.SaveFailedMessageShown)
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_subject)) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(CreateSubjectEvent.GoBack) }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.close_24),
                            contentDescription = "close"
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { onEvent(CreateSubjectEvent.Save) },
                        enabled = state.canSave
                    ) {
                        Text(stringResource(R.string.save))
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0)
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
                    onValueChange = { name -> onEvent(CreateSubjectEvent.NameChanged(name)) },
                    label = { Text(stringResource(R.string.name)) }
                )

                Spacer(modifier = Modifier.height(6.dp))
                OutlinedDatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.deadline,
                    label = stringResource(R.string.deadline),
                    pickerTitle = stringResource(R.string.select_deadline),
                    onSelectedDate = { date ->
                        onEvent(CreateSubjectEvent.DeadlineChanged(selectedDate = date))
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    value = state.description,
                    onValueChange = { description ->
                        onEvent(CreateSubjectEvent.DescriptionChanged(description))
                    },
                    label = { Text("${stringResource(R.string.description)} (${stringResource(R.string.optional)}) ") },
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "en"
)
@Composable
fun CreateSubjectDialogPreview() {
    AppTheme {
        CreateSubjectScreen(
            state = CreateSubjectState(
                description = "Professor Jujuba \nSeg 2h, Ter 4h, Qui 1h? \neu caguei nas calças"
            ),
            onEvent = {}
        )
    }
}