package com.g3tech.paceful.ui.shared.createstudy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.ui.home.CreateStudyEvent
import com.g3tech.paceful.ui.home.HomeScreenEvent
import com.g3tech.paceful.ui.shared.OutlinedDatePicker
import java.time.LocalDate

@Composable
fun TopicForm(name: String?, deadline: LocalDate?, enabled: Boolean, onEvent: (HomeScreenEvent) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "${stringResource(R.string.add_topic)} (${
                    stringResource(
                        R.string.optional
                    )
                })", style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name ?: "",
                onValueChange = { topicName ->
                    onEvent(
                        CreateStudyEvent.TopicNameChanged(
                            topicName
                        )
                    )
                },
                label = { Text(stringResource(R.string.name)) }
            )
            Spacer(Modifier.height(6.dp))
            OutlinedDatePicker(
                modifier = Modifier.fillMaxWidth(),
                value = deadline,
                label = "${stringResource(R.string.deadline)} (${stringResource(R.string.optional)})",
                onSelectedDate = { date ->
                    onEvent(CreateStudyEvent.TopicDeadlineChanged(date))
                }
            )

            Spacer(Modifier.height(12.dp))
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(CreateStudyEvent.AddTopic) },
                enabled = enabled
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_24),
                    contentDescription = stringResource(R.string.add_topic)
                )
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.add_topic))
            }
        }
    }
}
