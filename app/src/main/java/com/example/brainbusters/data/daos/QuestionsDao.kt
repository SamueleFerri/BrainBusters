package com.example.brainbusters.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brainbusters.data.entities.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionsDao {

    // Get all Questions
    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<Question>>

    // Get all Questions by Quiz id
    @Query("SELECT * FROM questions WHERE question_quizId = :quizId")
    fun getQuestionsByQuizId(quizId: Int): Flow<List<Question>>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(question: Question)

    // Remove
    @Query("DELETE FROM questions WHERE question_id = :quizId")
    suspend fun delete(quizId: Int)

    // Update
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(question: Question)
}