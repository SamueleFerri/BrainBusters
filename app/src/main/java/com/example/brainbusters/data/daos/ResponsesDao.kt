package com.example.brainbusters.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.brainbusters.data.entities.Response

@Dao
interface ResponsesDao {

    @Insert
    suspend fun insertResponse(response: Response)

    @Update
    suspend fun updateResponse(response: Response)

    @Delete
    suspend fun deleteResponse(response: Response)

    @Query("SELECT * FROM responses WHERE response_id = :responseId")
    suspend fun getResponseById(responseId: Int): Response?

    @Query("SELECT * FROM responses WHERE response_question_id = :questionId")
    suspend fun getResponsesByQuestionId(questionId: Int): List<Response>

    @Query("SELECT * FROM responses")
    suspend fun getAllResponses(): List<Response>
}