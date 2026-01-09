package com.g3tech.paceful.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.g3tech.paceful.domain.model.PartialSubject
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.input.CreateStudy
import com.g3tech.paceful.domain.model.input.CreateTopic
import com.g3tech.paceful.domain.repositories.StudyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

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
        when (event) {
            is HomeScreenEvent.NewStudyClick -> _state.update { it.copy(createStudyOpen = true) }
            is HomeScreenEvent.ToggleCreateStudyDialog -> _state.update { it.copy(createStudyOpen = event.isOpen) }

            is CreateStudyEvent.DeadlineChanged -> updateStudyDeadline(event.selectedDate)
            is CreateStudyEvent.StartDateChanged -> updateStartDate(event.selectedDate)
            is CreateStudyEvent.NameChanged -> updateStudyName(event.name)
            is CreateStudyEvent.SubjectChanged -> updateStudySubject(event.subject)
            is CreateStudyEvent.TopicNameChanged -> updateTopicName(event.topicName)
            is CreateStudyEvent.TopicDeadlineChanged -> updateTopicDeadline(event.topicDeadline)
            is CreateStudyEvent.AddTopic -> addTopic()
            is CreateStudyEvent.RemoveTopic -> removeTopic(event.topic)
            is CreateStudyEvent.Save -> saveStudy()
            is CreateStudyEvent.SaveFailedMessageShown -> _state.update { state ->
                state.copy(createStudyState = state.createStudyState.copy(saveFailed = false))
            }

            else -> {}
        }
    }

    private fun updateStudyDeadline(deadline: LocalDate?) {
        _state.update { state ->
            state.copy(createStudyState = state.createStudyState.copy(deadline = deadline))
        }
        validateStudy()
    }

    private fun updateStartDate(startDate: LocalDate) {
        _state.update { state ->
            state.copy(createStudyState = state.createStudyState.copy(startDate = startDate))
        }
    }

    private fun updateStudyName(name: String) {
        _state.update { state ->
            state.copy(createStudyState = state.createStudyState.copy(name = name))
        }
        validateStudy()
    }

    private fun updateStudySubject(subject: PartialSubject?) {
        _state.update { state ->
            state.copy(createStudyState = state.createStudyState.copy(selectedSubject = subject))
        }
    }

    private fun validateStudy() {
        val isNameValid = state.value.createStudyState.name.isNotBlank()
        val isDeadlineValid = state.value.createStudyState.deadline != null

        val canSave = isNameValid && isDeadlineValid
        _state.update { state ->
            state.copy(createStudyState = state.createStudyState.copy(canSave = canSave))
        }
    }

    private fun updateTopicName(topicName: String) {
        _state.update { state ->
            val existingTopic = state.createStudyState.currentTopic
            val updatedTopic = existingTopic.copy(name = topicName)

            state.copy(
                createStudyState = state.createStudyState.copy(currentTopic = updatedTopic)
            )
        }
        validateTopic()
    }

    private fun validateTopic() {
        val canAddTopic = state.value.createStudyState.currentTopic.name.isNotBlank()

        _state.update { state ->
            state.copy(createStudyState = state.createStudyState.copy(canAddTopic = canAddTopic))
        }
    }

    private fun updateTopicDeadline(deadline: LocalDate) {
        _state.update { state ->
            val existingTopic = state.createStudyState.currentTopic
            val updatedTopic = existingTopic.copy(deadline = deadline)

            state.copy(
                createStudyState = state.createStudyState.copy(currentTopic = updatedTopic)
            )
        }
        validateTopic()
    }

    private fun addTopic() {
        _state.update { state ->
            val newTopic = state.createStudyState.currentTopic
            val newTopicsList = state.createStudyState.topics?.plus(newTopic) ?: listOf(newTopic)
            val resetCurrentTopic = CreateTopic(name = "", null)
            state.copy(createStudyState = state.createStudyState.copy(topics = newTopicsList, currentTopic = resetCurrentTopic))
        }
    }

    private fun removeTopic(topic: CreateTopic) {
        _state.update { state ->
            val newTopicsList = state.createStudyState.topics?.minus(topic)
            val topicsListValue = if (newTopicsList.isNullOrEmpty()) null else newTopicsList
            state.copy(createStudyState = state.createStudyState.copy(topics = topicsListValue))
        }
    }

    private fun saveStudy() {
        _state.update { state -> state.copy(createStudyState = state.createStudyState.copy(isLoading = true)) }

        viewModelScope.launch {
            val startDate = state.value.createStudyState.startDate
            val studyStatusId = if (startDate == LocalDate.now()) {
                StudyStatus.SCHEDULED.id
            } else StudyStatus.PENDING.id
            val newStudy = CreateStudy(
                subject = state.value.createStudyState.selectedSubject?.id,
                name = state.value.createStudyState.name,
                status = studyStatusId,
                deadline = state.value.createStudyState.deadline ?: LocalDate.now(),
                topics = state.value.createStudyState.topics
            )
            val result = studyRepository.registerStudy(newStudy)
            result.onSuccess {
                 _state.update { state -> state.copy(createStudyOpen = false) }
            }
            result.onFailure {
                _state.update { state -> state.copy(createStudyState = state.createStudyState.copy(saveFailed = true)) }
            }

            _state.update { state -> state.copy(createStudyState = state.createStudyState.copy(isLoading = false)) }
        }
    }
}