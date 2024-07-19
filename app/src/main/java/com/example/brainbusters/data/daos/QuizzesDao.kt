package com.example.brainbusters.data.daos

import androidx.room.*
import com.example.brainbusters.data.entities.Quiz
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizzesDao {

   @Query("SELECT * FROM quizzes")
   fun getAllQuizzes(): Flow<List<Quiz>>

   @Query("SELECT * FROM quizzes WHERE quiz_category = :category")
   fun getAllQuizInCategory(category: String): Flow<List<Quiz>>

   @Query("SELECT * FROM quizzes WHERE quiz_id = :quizId")
   fun getQuizById(quizId: Int): Flow<Quiz>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(quiz: Quiz)

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(allQuizzes: List<Quiz>)

   @Update
   suspend fun update(quiz: Quiz)

   @Query("DELETE FROM quizzes WHERE quiz_id = :quizId")
   suspend fun delete(quizId: Int)

   @Query("SELECT * FROM quizzes WHERE is_favorite = 1")
   fun getFavoriteQuizzes(): Flow<List<Quiz>>
}