package com.g3tech.paceful.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.g3tech.paceful.ui.home.components.Greeting
import com.g3tech.paceful.ui.home.components.StudyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Novo estudo")
                Text(text = "Novo estudo")
            }
        },
        topBar = {
            TopAppBar(
                title = { Greeting() },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(PaddingValues(horizontal = 24.dp)))
        {
            Text(text = "Paceful")
            StudyCard()
        }
    }
}