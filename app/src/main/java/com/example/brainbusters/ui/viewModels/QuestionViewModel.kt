package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Question
import com.example.brainbusters.data.repositories.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class QuestionViewModel(private val repository: QuestionRepository) : ViewModel() {

    fun getQuestionsByQuizId(quizId: Int): Flow<List<Question>> {
        return repository.getQuestionsByQuizId(quizId)
    }

    fun insert(question: Question) {
        viewModelScope.launch {
            repository.insert(question)
        }
    }

    fun deleteQuestion(questionId: Int) {
        viewModelScope.launch {
            repository.deleteQuestion(questionId)
        }
    }
}