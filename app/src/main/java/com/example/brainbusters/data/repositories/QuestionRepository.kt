package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.QuestionsDao
import com.example.brainbusters.data.entities.Question
import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val questionDao: QuestionsDao) {

    fun getQuestionsByQuizId(quizId: Int): Flow<List<Question>> {
        return questionDao.getQuestionsByQuizId(quizId)
    }

    suspend fun insert(question: Question) {
        questionDao.insert(question)
    }

    suspend fun deleteQuestion(questionId: Int) {
        questionDao.deleteQuestion(questionId)
    }
}