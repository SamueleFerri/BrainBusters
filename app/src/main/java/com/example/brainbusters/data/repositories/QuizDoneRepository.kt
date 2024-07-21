package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.QuizzesDoneDao
import com.example.brainbusters.data.entities.QuizDone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

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

    suspend fun insertOrUpdate(quizDone: QuizDone) {
        val existingQuizDone = quizzesDoneDao.getQuizDoneByUserIdAndQuizId(quizDone.userId, quizDone.quizId).first()
        if (existingQuizDone != null) {
            if (existingQuizDone.score < quizDone.score) {
                quizzesDoneDao.update(quizDone.copy(id = existingQuizDone.id))
            }
        } else {
            quizzesDoneDao.insert(quizDone)
        }
    }

    suspend fun delete(id: Int) {
        quizzesDoneDao.delete(id)
    }
}