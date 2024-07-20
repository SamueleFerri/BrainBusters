package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.ResponsesDao
import com.example.brainbusters.data.entities.Response

class ResponseRepository(private val responseDao: ResponsesDao) {

    suspend fun insertResponse(response: Response) {
        responseDao.insertResponse(response)
    }

    suspend fun updateResponse(response: Response) {
        responseDao.updateResponse(response)
    }

    suspend fun deleteResponse(response: Response) {
        responseDao.deleteResponse(response)
    }

    suspend fun getResponseById(responseId: Int): Response? {
        return responseDao.getResponseById(responseId)
    }

    suspend fun getResponsesByQuestionId(questionId: Int): List<Response> {
        return responseDao.getResponsesByQuestionId(questionId)
    }

    suspend fun getAllResponses(): List<Response> {
        return responseDao.getAllResponses()
    }
}