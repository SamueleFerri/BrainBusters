package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Quiz
import com.example.brainbusters.data.repositories.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            quizRepository.quizzes.collect { quizzes ->
                _state.value = _state.value.copy(quizzes = quizzes)
            }
        }
    }

    suspend fun getIdQuizByTitle(title: String): Int? {
        return quizRepository.getAllQuizzes().firstOrNull()?.find { it.title == title }?.quizId
    }

    fun getQuizzesByCategory(category: String) = quizRepository.getAllQuizInCategory(category)

    fun getFavoriteQuizzes() = quizRepository.getFavoriteQuizzes()

    fun updateQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizRepository.updateQuiz(quiz)
        }
    }
}

data class QuizState(
    val quizzes: List<Quiz> = emptyList()
)