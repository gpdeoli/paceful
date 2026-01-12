package com.g3tech.paceful.ui.study.createstudy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import com.g3tech.paceful.domain.model.StudyStatus
import com.g3tech.paceful.domain.model.input.CreateStudy
import com.g3tech.paceful.domain.model.input.CreateTopic
import com.g3tech.paceful.domain.model.input.subject.SubjectToSelect
import com.g3tech.paceful.domain.repositories.StudyRepository
import com.g3tech.paceful.domain.repositories.SubjectRepository
import com.g3tech.paceful.ui.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class CreateStudyViewModel(
    private val subjectRepository: SubjectRepository,
    private val studyRepository: StudyRepository,
    private val navigator: Navigator<NavKey>
) : ViewModel() {
    private val _state = MutableStateFlow(CreateStudyState())
    val state = _state
        .onStart { loadData() }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            CreateStudyState()
        )

    private fun loadData() {
        viewModelScope.launch {
            val result = subjectRepository.getPartialSubjects()
            result.onSuccess { subjects ->
                _state.update { state -> state.copy(subjectOptions = subjects) }
            }
            result.onFailure {
                // TODO()
            }
        }
    }

    fun onEvent(event: CreateStudyEvent) {
        when (event) {
            is CreateStudyEvent.NameChanged -> updateStudyName(event.name)
            is CreateStudyEvent.DeadlineChanged -> updateStudyDeadline(event.selectedDate)
            is CreateStudyEvent.StartDateChanged -> updateStartDate(event.selectedDate)
            is CreateStudyEvent.SubjectChanged -> updateStudySubject(event.subject)
            is CreateStudyEvent.TopicNameChanged -> updateTopicName(event.topicName)
            is CreateStudyEvent.TopicDeadlineChanged -> updateTopicDeadline(event.topicDeadline)
            is CreateStudyEvent.AddTopic -> addTopic()
            is CreateStudyEvent.RemoveTopic -> removeTopic(event.topic)
            is CreateStudyEvent.Save -> saveStudy()
            is CreateStudyEvent.SaveFailedMessageShown -> _state.update { state ->
                state.copy(saveFailed = false)
            }
            is CreateStudyEvent.GoBack -> navigator.goBack()
        }
    }

    private fun updateStudyDeadline(deadline: LocalDate?) {
        _state.update { state -> state.copy(deadline = deadline) }
        validateStudy()
    }

    private fun updateStartDate(startDate: LocalDate) {
        _state.update { state -> state.copy(startDate = startDate) }
    }

    private fun updateStudyName(name: String) {
        _state.update { state -> state.copy(name = name) }
        validateStudy()
    }

    private fun updateStudySubject(subject: SubjectToSelect?) {
        _state.update { state -> state.copy(selectedSubject = subject) }
    }

    private fun validateStudy() {
        val isNameValid = state.value.name.isNotBlank()
        val isDeadlineValid = state.value.deadline != null

        val canSave = isNameValid && isDeadlineValid
        _state.update { state ->
            state.copy(canSave = canSave)
        }
    }

    private fun updateTopicName(topicName: String) {
        _state.update { state ->
            val existingTopic = state.currentTopic
            val updatedTopic = existingTopic.copy(name = topicName)

            state.copy(currentTopic = updatedTopic)
        }
        validateTopic()
    }

    private fun validateTopic() {
        val canAddTopic = state.value.currentTopic.name.isNotBlank()

        _state.update { state -> state.copy(canAddTopic = canAddTopic) }
    }

    private fun updateTopicDeadline(deadline: LocalDate) {
        _state.update { state ->
            val existingTopic = state.currentTopic
            val updatedTopic = existingTopic.copy(deadline = deadline)

            state.copy(currentTopic = updatedTopic)
        }
        validateTopic()
    }

    private fun addTopic() {
        _state.update { state ->
            val newTopic = state.currentTopic
            val newTopicsList = state.topics?.plus(newTopic) ?: listOf(newTopic)
            val resetCurrentTopic = CreateTopic(name = "", null)
            state.copy(topics = newTopicsList, currentTopic = resetCurrentTopic)
        }
    }

    private fun removeTopic(topic: CreateTopic) {
        _state.update { state ->
            val newTopicsList = state.topics?.minus(topic)
            val topicsListValue = if (newTopicsList.isNullOrEmpty()) null else newTopicsList
            state.copy(topics = topicsListValue)
        }
    }

    private fun saveStudy() {
        _state.update { state -> state.copy(isLoading = true) }

        viewModelScope.launch {
            val startDate = state.value.startDate
            val studyStatusId = if (startDate == LocalDate.now()) {
                StudyStatus.SCHEDULED.id
            } else StudyStatus.PENDING.id
            val newStudy = CreateStudy(
                subject = state.value.selectedSubject?.id,
                name = state.value.name,
                status = studyStatusId,
                deadline = state.value.deadline ?: LocalDate.now(),
                topics = state.value.topics
            )
            val result = studyRepository.registerStudy(newStudy)
            result.onSuccess {
                navigator.goBack()
            }
            result.onFailure {
                _state.update { state -> state.copy(saveFailed = true) }
            }
        }
    }
}