package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.QuizzesDao
import com.example.brainbusters.data.entities.Quiz
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val quizzesDAO: QuizzesDao) {

    val quizzes: Flow<List<Quiz>> = quizzesDAO.getAllQuizzes()

    suspend fun insertNewQuiz(quiz: Quiz) {
        quizzesDAO.insert(quiz)
    }

    suspend fun deleteQuizById(quizId: Int) {
        quizzesDAO.delete(quizId)
    }

    fun getAllQuizInCategory(category: String) {
        quizzesDAO.getAllQuizInCategory(category)
    }

    fun getQuizById(quizId: Int) {
        quizzesDAO.getQuizById(quizId)
    }
}