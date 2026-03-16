package com.example.kts_project.presentation.viewmodel.mainviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kts_project.domain.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val coursesRepository: CourseRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    private val searchQuery = MutableStateFlow("")

    init {
        searchQuery
            .debounce(300)
            .flatMapLatest { query ->
                flow {
                    _state.update { it.copy(isLoading = true, errorType = null) }
                    emit(coursesRepository.getCourses(page = 1, search = query) )
                }
            }
            .onEach { result ->
                result
                    .onSuccess { coursePage ->
                        _state.update { it.copy(
                            isLoading = false,
                            courses = coursePage.courses,
                            hasNext = coursePage.hasNext,
                            currentPage = coursePage.currentPage
                        ) }
                    }
                    .onFailure {
                        _state.update { it.copy(isLoading = false, errorType = ErrorType.LOAD_ERROR) }
                    }
            }
            .launchIn(viewModelScope)
    }


    fun onSearchQueryChanged(search: String) {
        searchQuery.value = search
    }

    fun getCourseById(id: Int) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true, errorType = null) }
                coursesRepository.getCourseById(id = id)
                .onSuccess { course ->
                    _state.update { it.copy(isLoading = false, selectedCourse = course) }
                }
                .onFailure { e->
                    _state.update{it.copy(isLoading = false, errorType = ErrorType.LOAD_ERROR)}
                }
        }
    }

    fun loadMore() {
        if (_state.value.isLoadingMore && !_state.value.hasNext)
            return

        viewModelScope.launch {
            _state.update { it.copy(isLoadingMore = true) }

            coursesRepository.getCourses(
                page = _state.value.currentPage + 1,
                search = searchQuery.value
            )
                .onSuccess { coursePage ->
                    _state.update {
                        it.copy(
                            isLoadingMore = false,
                            courses = it.courses + coursePage.courses,
                            hasNext = coursePage.hasNext,
                            currentPage = coursePage.currentPage
                        )
                    }
                }
                .onFailure {
                    _state.update { it.copy( isLoadingMore = false) }
                }
        }
    }
}





