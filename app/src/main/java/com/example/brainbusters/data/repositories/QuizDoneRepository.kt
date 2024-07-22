package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.QuizzesDoneDao
import com.example.brainbusters.data.daos.QuizzesDao
import com.example.brainbusters.data.entities.QuizDone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class QuizDoneRepository(
    private val quizzesDoneDao: QuizzesDoneDao,
    private val quizDao: QuizzesDao // Aggiungi l'istanza di QuizDao
) {
    fun getQuizDoneById(id: Int): Flow<QuizDone> {
        return quizzesDoneDao.getQuizDoneById(id)
    }

    fun getQuizzesDoneByUserId(userId: Int): Flow<List<QuizDone>> {
        return quizzesDoneDao.getQuizzesDoneByUserId(userId)
    }

    fun getQuizzesDoneByQuizId(quizId: Int): Flow<List<QuizDone>> {
        return quizzesDoneDao.getQuizzesDoneByQuizId(quizId)
    }

    fun getNumQuizDoneByCategory(userId: Int): Flow<Map<String, Int>> {
        return quizzesDoneDao.getQuizCountsByCategory(userId).map { categoryCounts ->
            categoryCounts.associate { it.category to it.count }
        }
    }

    fun getQuizzesDoneByCategory(userId: Int, category: String): Flow<List<QuizDone>> {
        return quizzesDoneDao.getQuizzesDoneByUserId(userId)
            .map { quizDoneList ->
                quizDoneList.filter { quizDone ->
                    val quiz = quizDao.getQuizById(quizDone.quizId).first()
                    quiz.categoryName == category
                }
            }
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