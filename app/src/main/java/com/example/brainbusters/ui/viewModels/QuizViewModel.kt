package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Quiz
import com.example.brainbusters.data.repositories.QuizRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class QuizzesState(
    val quizzes: List<Quiz> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

interface QuizzesActions {
    fun addQuiz(quiz: Quiz): Job
    fun removeQuiz(quizId: Int): Job
    fun getQuizzesByCategory(category: String): Flow<List<Quiz>>
    fun getQuizById(quizId: Int): Flow<List<Quiz>>
    fun populateQuizzes(): Job
}

class QuizViewModel(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _state = MutableStateFlow(QuizzesState())
    val state: StateFlow<QuizzesState> = _state.asStateFlow()

    fun getQuizzesByCategory(category: String): Flow<List<Quiz>> {
        return quizRepository.getAllQuizInCategory(category)
    }

    val actions = object : QuizzesActions {
        override fun addQuiz(quiz: Quiz) = viewModelScope.launch {
            try {
                quizRepository.insertNewQuiz(quiz)
                populateQuizzes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }

        override fun removeQuiz(quizId: Int) = viewModelScope.launch {
            try {
                quizRepository.deleteQuizById(quizId)
                populateQuizzes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }

        override fun getQuizzesByCategory(category: String): Flow<List<Quiz>> {
            return quizRepository.getAllQuizInCategory(category)
        }

        override fun getQuizById(quizId: Int): Flow<List<Quiz>> {
            return quizRepository.getQuizById(quizId)
        }

        override fun populateQuizzes() = viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                quizRepository.quizzes.collect { quizzes ->
                    _state.value = QuizzesState(quizzes = quizzes, isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = QuizzesState(isLoading = false, error = e.message)
            }
        }
    }

    init {
        actions.populateQuizzes()
    }
}