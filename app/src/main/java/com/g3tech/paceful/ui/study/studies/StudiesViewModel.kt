package com.g3tech.paceful.ui.study.studies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import com.g3tech.paceful.domain.model.StudyStatus
import java.time.LocalDate
import com.g3tech.paceful.domain.model.input.GetStudiesQueryParams
import com.g3tech.paceful.domain.repositories.StudyRepository
import com.g3tech.paceful.domain.repositories.SubjectRepository
import com.g3tech.paceful.ui.navigation.Navigator
import com.g3tech.paceful.ui.navigation.Routes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudiesViewModel(
    subjectId: Long?,
    studyStatuses: Set<StudyStatus>,
    startDate: LocalDate?,
    endDate: LocalDate?,
    private val studyRepository: StudyRepository,
    private val subjectRepository: SubjectRepository,
    private val navigator: Navigator<NavKey>,
) : ViewModel() {

    private val _state = MutableStateFlow(
        StudiesState(
            studyStatusFilter = studyStatuses,
            subjectIdFilter = subjectId,
            startDateFilter = startDate,
            endDateFilter = endDate,
        )
    )
    val state = _state
        .onStart {
            loadSubjects()
            loadStudies(resetPage = true)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), StudiesState())

    private var loadJob: Job? = null

    private fun loadSubjects() {
        viewModelScope.launch {
            subjectRepository.getSubjectsToSelect().onSuccess { subjects ->
                _state.update { it.copy(subjectOptions = subjects) }
            }
        }
    }

    private fun loadStudies(resetPage: Boolean) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            if (resetPage) {
                _state.update {
                    it.copy(
                        isLoading = true,
                        studies = emptyList(),
                        currentPage = 0,
                        hasMore = true,
                    )
                }
            } else {
                _state.update { it.copy(isLoadingMore = true) }
            }

            val current = _state.value
            studyRepository.getStudies(
                params = GetStudiesQueryParams(
                    searchQuery = current.searchQueryFilter,
                    startDate = current.startDateFilter,
                    endDate = current.endDateFilter,
                    statuses = current.studyStatusFilter,
                    subjectId = current.subjectIdFilter,
                    page = current.currentPage,
                    pageSize = PAGE_SIZE,
                )
            ).onSuccess { newStudies ->
                _state.update { state ->
                    state.copy(
                        studies = state.studies + newStudies,
                        currentPage = state.currentPage + 1,
                        hasMore = newStudies.size >= PAGE_SIZE,
                        isLoading = false,
                        isLoadingMore = false,
                    )
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false, isLoadingMore = false) }
            }
        }
    }

    fun onEvent(event: StudiesEvent) {
        when (event) {
            is StudiesEvent.SearchQueryChanged -> {
                _state.update { it.copy(searchQueryFilter = event.query) }
                loadStudies(resetPage = true)
            }

            is StudiesEvent.StatusToggled -> {
                _state.update { state ->
                    val newStatuses = when {
                        event.status == null -> emptySet()
                        event.status in state.studyStatusFilter -> state.studyStatusFilter - event.status
                        else -> state.studyStatusFilter + event.status
                    }
                    state.copy(studyStatusFilter = newStatuses)
                }
                loadStudies(resetPage = true)
            }

            is StudiesEvent.ApplyFilters -> {
                _state.update {
                    it.copy(
                        searchQueryFilter = event.searchQuery,
                        studyStatusFilter = event.statuses,
                        subjectIdFilter = event.subjectId,
                        startDateFilter = event.startDate,
                        endDateFilter = event.endDate,
                        isFilterSheetVisible = false,
                    )
                }
                loadStudies(resetPage = true)
            }

            StudiesEvent.ToggleFilterSheet -> {
                _state.update { it.copy(isFilterSheetVisible = !it.isFilterSheetVisible) }
            }

            StudiesEvent.LoadMore -> {
                val s = _state.value
                if (s.hasMore && !s.isLoadingMore && !s.isLoading) {
                    loadStudies(resetPage = false)
                }
            }

            StudiesEvent.NewStudyClick -> navigator.navigateTo(Routes.CreateStudyScreen)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
