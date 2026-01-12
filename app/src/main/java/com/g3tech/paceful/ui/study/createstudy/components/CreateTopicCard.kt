package com.g3tech.paceful.ui.study.createstudy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.R
import com.g3tech.paceful.domain.model.input.CreateTopic
import com.g3tech.paceful.ui.utils.getLocalizedDateFormatter

@Composable
fun CreateTopicCard(topic: CreateTopic, onRemove: (CreateTopic) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(topic.name)
                    if (topic.deadline != null) {
                        Text(
                            "${stringResource(R.string.due)}: ${
                                topic.deadline.format(
                                    getLocalizedDateFormatter()
                                )
                            }"
                        )
                    }
                }
                IconButton(onClick = { onRemove(topic) }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.close_24),
                        contentDescription = "remove topic"
                    )
                }
            }
        }
    }
}
