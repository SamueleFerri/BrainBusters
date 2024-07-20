package com.example.brainbusters.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.brainbusters.data.entities.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {

    @Insert
    suspend fun insert(question: Question)

    @Query("SELECT * FROM questions WHERE question_quizId = :quizId")
    fun getQuestionsByQuizId(quizId: Int): Flow<List<Question>>

    @Query("DELETE FROM questions WHERE question_id = :questionId")
    suspend fun deleteQuestion(questionId: Int)
}