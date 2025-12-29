package com.g3tech.paceful.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.g3tech.paceful.domain.repositories.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val studyRepository: StudyRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state
        .onStart { loadData() }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenState()
        )

    private fun loadData() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = studyRepository.getStudiesSummary()
            result.onSuccess { data ->
                _state.update { it.copy(summaryData = data) }
            }.onFailure {
                // TODO()
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onEvent(event: HomeScreenEvent) {

    }
}