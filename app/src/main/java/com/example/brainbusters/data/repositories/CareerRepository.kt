package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.CareersDao
import com.example.brainbusters.data.entities.Career
import kotlinx.coroutines.flow.Flow

class CareerRepository(private val careersDAO: CareersDao) {

    val careers: Flow<List<Career>> = careersDAO.getAllCareers()

    suspend fun insertNewCareer(career: Career) {
        careersDAO.insert(career)
    }

    suspend fun deleteCareerByUserId(userId: Int) {
        careersDAO.delete(userId)
    }

    fun getCareerByUserId(userId: Int) {
        careersDAO.getUserByUserId(userId)
    }

    fun  getScoreBoard() {
        careersDAO.getScoreBoardCareers()
    }
}