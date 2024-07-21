package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.QuizDone
import com.example.brainbusters.data.repositories.QuizDoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class QuizDoneViewModel(private val quizDoneRepository: QuizDoneRepository) : ViewModel() {

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

    fun delete(id: Int) {
        viewModelScope.launch {
            quizDoneRepository.delete(id)
        }
    }
}