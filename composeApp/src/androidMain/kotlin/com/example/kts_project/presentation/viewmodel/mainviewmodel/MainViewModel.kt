package com.example.kts_project.presentation.viewmodel.mainviewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kts_project.domain.model.Post
import com.example.kts_project.domain.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    init {
        loadPosts()
    }

    fun loadPosts() {
        postsRepository.getPosts()
            .onStart {
                _state.update { it.copy(isLoading = true) }
            }
            .onEach { posts ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        posts = posts,
                        errorType = null
                    )
                }
            }
            .catch { e ->
                _state.update {
                    it.copy(isLoading = false, errorType = ErrorType.LOAD_ERROR)
                }
            }
            .launchIn(viewModelScope)
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true, errorType = null) }

            val result = postsRepository.refreshPosts()
            result.fold(
                onSuccess = { posts ->
                    _state.update {
                        it.copy(posts = posts, isRefreshing = false)
                    }
                },
                onFailure = { e ->
                    _state.update { it.copy(errorType = ErrorType.REFRESH_ERROR) }
                }
            )
        }
    }


}





