package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.QuizDone
import com.example.brainbusters.data.repositories.QuizDoneRepository
import com.example.brainbusters.data.repositories.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuizDoneViewModel(
    private val quizDoneRepository: QuizDoneRepository,
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _numQuizDoneByCategory = MutableStateFlow<Map<String, Int>>(emptyMap())
    val numQuizDoneByCategory: StateFlow<Map<String, Int>> get() = _numQuizDoneByCategory

    fun getQuizDoneById(id: Int): Flow<QuizDone> {
        return quizDoneRepository.getQuizDoneById(id)
    }

    fun getQuizzesDoneByUserId(userId: Int): Flow<List<QuizDone>> {
        return quizDoneRepository.getQuizzesDoneByUserId(userId)
    }

    fun getQuizzesDoneByQuizId(quizId: Int): Flow<List<QuizDone>> {
        return quizDoneRepository.getQuizzesDoneByQuizId(quizId)
    }

    fun insertOrUpdate(quizDone: QuizDone) {
        viewModelScope.launch {
            quizDoneRepository.insertOrUpdate(quizDone)
        }
    }

    fun loadNumQuizDoneByCategory(userId: Int) {
        viewModelScope.launch {
            val categories = quizRepository.getAllCategories().first()
            val result = mutableMapOf<String, Int>()

            categories.forEach { category ->
                val count = quizDoneRepository.getQuizzesDoneByCategory(userId, category)
                    .map { it.size }
                    .first()
                result[category] = count
            }

            _numQuizDoneByCategory.value = result
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            quizDoneRepository.delete(id)
        }
    }
}