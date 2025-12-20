package com.g3tech.paceful.ui.home

import androidx.lifecycle.ViewModel
import com.g3tech.paceful.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val navigator: Navigator) : ViewModel() {
}