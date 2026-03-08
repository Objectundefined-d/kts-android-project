package com.example.kts_project.presentation.viewmodel.mainviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_project.domain.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coursesRepository: CourseRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    init {
        search(page = 1, search = null)
    }

    private var currentSearchJob: Job? = null

    fun search(page: Int, search: String?) {
        currentSearchJob?.cancel()
        _state.update { it.copy(isLoading = true, errorType = null) }

        currentSearchJob = viewModelScope.launch {
            coursesRepository.getCourses(page = page, search = search)
                .onSuccess { coursePage ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            courses = coursePage.courses
                        )
                    }
                }
                .onFailure { e ->
                    if (e is CancellationException) throw e
                    _state.update {
                        it.copy(isLoading = false, errorType = ErrorType.LOAD_ERROR)
                    }
                }
        }
    }

    fun getCourseById(id: Int) {
        currentSearchJob?.cancel()
        _state.update { it.copy(isLoading = true, errorType = null) }

        currentSearchJob = viewModelScope.launch {
            coursesRepository.getCourseById(id = id)
                .onSuccess { course ->
                    _state.update { it.copy(isLoading = false, selectedCourse = course) }
                }
                .onFailure { e->
                    if (e is CancellationException) throw e

                    _state.update{it.copy(isLoading = false, errorType = ErrorType.LOAD_ERROR)}
                }
        }
    }
}





