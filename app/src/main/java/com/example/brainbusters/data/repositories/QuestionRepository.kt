package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.QuestionsDao
import com.example.brainbusters.data.entities.Question
import kotlinx.coroutines.flow.Flow

class QuestionRepository (private val questionsDao: QuestionsDao) {

    val questions: Flow<List<Question>> = questionsDao.getAllQuestions()

    suspend fun addNewQuestion(question: Question) {
        questionsDao.insert(question)
    }

    suspend fun deleteQuestionByQuestionId(questionId: Int) {
        questionsDao.delete(questionId)
    }

    fun getQuestionsByQuizId(quizId: Int) {
        questionsDao.getQuestionsByQuizId(quizId)
    }
}