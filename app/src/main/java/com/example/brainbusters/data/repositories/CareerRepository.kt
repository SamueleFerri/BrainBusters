package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.CareersDao
import com.example.brainbusters.data.entities.Career
import kotlinx.coroutines.flow.Flow

class CareerRepository(private val careersDAO: CareersDao) {

    val careers: Flow<List<Career>> = careersDAO.getAllCareers()

    suspend fun insertNewCareer(career: Career) {
        careersDAO.insert(career)
    }

    suspend fun updateCareer(career: Career) {
        careersDAO.update(career)
    }

    suspend fun deleteCareerByUserId(userId: Int) {
        careersDAO.delete(userId)
    }

    fun getCareerByUserId(userId: Int): Flow<Career?> {
        return careersDAO.getCareerById(userId)
    }

    fun getScoreBoard(): Flow<List<Career>> {
        return careersDAO.getScoreBoardCareers()
    }
}