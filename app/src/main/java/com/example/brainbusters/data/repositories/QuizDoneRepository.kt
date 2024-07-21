package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.QuizzesDoneDao
import com.example.brainbusters.data.entities.QuizDone
import kotlinx.coroutines.flow.Flow

class QuizDoneRepository(private val quizzesDoneDao: QuizzesDoneDao) {

    fun getQuizDoneById(id: Int): Flow<QuizDone> {
        return quizzesDoneDao.getQuizDoneById(id)
    }

    fun getQuizzesDoneByUserId(userId: Int): Flow<List<QuizDone>> {
        return quizzesDoneDao.getQuizzesDoneByUserId(userId)
    }

    fun getQuizzesDoneByQuizId(quizId: Int): Flow<List<QuizDone>> {
        return quizzesDoneDao.getQuizzesDoneByQuizId(quizId)
    }

    suspend fun insert(quizDone: QuizDone) {
        quizzesDoneDao.insert(quizDone)
    }

    suspend fun delete(id: Int) {
        quizzesDoneDao.delete(id)
    }
}