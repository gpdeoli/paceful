package com.g3tech.paceful.ui.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import com.g3tech.paceful.domain.model.Subject
import com.g3tech.paceful.domain.repositories.SubjectRepository
import com.g3tech.paceful.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class CreateSubjectViewModel(
    private val subjectRepository: SubjectRepository,
    private val navigator: Navigator<NavKey>
) : ViewModel() {

    private val _state = MutableStateFlow(CreateSubjectState())
    val state =
        _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CreateSubjectState())

    fun onEvent(event: CreateSubjectEvent) {
        when (event) {
            is CreateSubjectEvent.NameChanged -> updateName(event.name)
            is CreateSubjectEvent.DeadlineChanged -> updateDeadline(event.selectedDate)
            is CreateSubjectEvent.DescriptionChanged -> updateDescription(event.description)
            is CreateSubjectEvent.Save -> save()
            is CreateSubjectEvent.SaveFailedMessageShown -> resetError()
            is CreateSubjectEvent.GoBack -> navigator.goBack()
        }
    }

    private fun updateName(name: String) {
        _state.update { state -> state.copy(name = name) }
        validateForm()
    }

    private fun updateDeadline(deadline: LocalDate) {
        _state.update { state -> state.copy(deadline = deadline) }
        validateForm()
    }

    private fun updateDescription(description: String) {
        _state.update { state -> state.copy(description = description) }
    }

    private fun validateForm() {
        val isNameValid = state.value.name.isNotBlank()
        val isDeadlineValid = state.value.deadline != null
        val canSave = isNameValid && isDeadlineValid
        _state.update { state -> state.copy(canSave = canSave) }
    }

    private fun save() {
        _state.update { state -> state.copy(isLoading = true) }
        viewModelScope.launch {
            val subject = Subject(
                name = state.value.name.trim(),
                deadline = state.value.deadline ?: LocalDate.now(),
                description = state.value.description.trim()
            )

            val result = subjectRepository.registerSubject(subject)
            result.onSuccess { navigator.goBack() }
                .onFailure { _state.update { state -> state.copy(saveFailed = false) } }
        }
    }

    private fun resetError() {
        _state.update { state -> state.copy(saveFailed = false) }
    }
}