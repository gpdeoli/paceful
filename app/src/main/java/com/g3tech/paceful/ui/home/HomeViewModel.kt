package com.g3tech.paceful.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import com.g3tech.paceful.domain.repositories.StudyRepository
import com.g3tech.paceful.ui.navigation.Navigator
import com.g3tech.paceful.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val studyRepository: StudyRepository,
    private val navigator: Navigator<NavKey>
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
        when (event) {
            is HomeScreenEvent.NewStudyClick -> navigator.navigateTo(Routes.CreateStudyScreen)
            is HomeScreenEvent.NewSubjectClick -> navigator.navigateTo(Routes.CreateSubjectScreen)
            is HomeScreenEvent.ViewAllClick -> navigator.navigateTo(Routes.StudyScreen(
                subjectId = event.subjectId,
                status = event.status,
                startDate = event.startDate?.toString(),
                endDate = event.endDate?.toString(),
            ))
        }
    }

}