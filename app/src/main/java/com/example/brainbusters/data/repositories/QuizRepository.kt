package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.QuizzesDao
import com.example.brainbusters.data.entities.Quiz
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val quizzesDAO: QuizzesDao) {

    val quizzes: Flow<List<Quiz>> = quizzesDAO.getAllQuizzes()

    suspend fun insertNewQuiz(quiz: Quiz) {
        quizzesDAO.insert(quiz)
    }
    fun getAllCategories(): Flow<List<String>> {
        return quizzesDAO.getAllCategories()
    }

    suspend fun deleteQuizById(quizId: Int) {
        quizzesDAO.delete(quizId)
    }

    fun getAllQuizInCategory(category: String): Flow<List<Quiz>> {
        return quizzesDAO.getAllQuizInCategory(category)
    }
    fun getAllQuizzes(): Flow<List<Quiz>> {
        return quizzesDAO.getAllQuizzes()
    }
    fun getQuizById(quizId: Int): Flow<Quiz> {
        return quizzesDAO.getQuizById(quizId)
    }

    suspend fun insertAllQuizzes(allQuizzes: List<Quiz>) {
        quizzesDAO.insertAll(allQuizzes)
    }

    suspend fun updateQuiz(quiz: Quiz) {
        quizzesDAO.update(quiz)
    }

    fun getFavoriteQuizzes(): Flow<List<Quiz>> {
        return quizzesDAO.getFavoriteQuizzes()
    }
}