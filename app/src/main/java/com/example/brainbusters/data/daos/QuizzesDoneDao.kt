package com.example.brainbusters.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brainbusters.data.entities.QuizDone
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizzesDoneDao {

    @Query("SELECT * FROM quizzes_done WHERE quizDone_id = :id")
    fun getQuizDoneById(id: Int): Flow<QuizDone>

    @Query("SELECT * FROM quizzes_done WHERE quizDone_userId = :userId")
    fun getQuizzesDoneByUserId(userId: Int): Flow<List<QuizDone>>

    @Query("SELECT * FROM quizzes_done WHERE quizDone_quizId = :quizId")
    fun getQuizzesDoneByQuizId(quizId: Int): Flow<List<QuizDone>>

    @Query("SELECT * FROM quizzes_done WHERE quizDone_userId = :userId AND quizDone_quizId = :quizId LIMIT 1")
    fun getQuizDoneByUserIdAndQuizId(userId: Int, quizId: Int): Flow<QuizDone?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(quizDone: QuizDone)

    @Update
    suspend fun update(quizDone: QuizDone)

    @Query("DELETE FROM quizzes_done WHERE quizDone_id = :id")
    suspend fun delete(id: Int)

    @Query("""
        SELECT q.quiz_category AS category, COUNT(*) AS count 
        FROM quizzes_done qd 
        JOIN quizzes q ON qd.quizDone_quizId = q.quiz_id 
        WHERE qd.quizDone_userId = :userId
        GROUP BY q.quiz_category
    """)
    fun getQuizCountsByCategory(userId: Int): Flow<List<CategoryCount>>
}

data class CategoryCount(
    val category: String,
    val count: Int
)