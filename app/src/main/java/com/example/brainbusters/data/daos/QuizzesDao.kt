package com.example.brainbusters.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brainbusters.data.entities.Quiz
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizzesDao {

   @Query("SELECT * FROM quizzes WHERE quiz_id = :quizId")
   fun getQuizById(quizId: Int): Flow<Quiz>

   @Query("SELECT * FROM quizzes")
   fun getAllQuizzes(): Flow<List<Quiz>>

   @Query("SELECT * FROM quizzes WHERE quiz_category = :category")
   fun getAllQuizInCategory(category: String): Flow<List<Quiz>>

   // Insert Quiz
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insert(quiz: Quiz)

   // Update Quiz
   @Update(onConflict = OnConflictStrategy.IGNORE)
   suspend fun update(quiz: Quiz)

   // Delete Quiz
   @Query("DELETE FROM quizzes WHERE quiz_id = :quizId")
   suspend fun delete(quizId: Int)

}