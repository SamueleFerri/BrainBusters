package com.example.brainbusters.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brainbusters.data.entities.Career
import kotlinx.coroutines.flow.Flow

@Dao
interface CareerDao {

    // Get Career by id
    @Query("SELECT * FROM careers WHERE career_id = :careerID")
    fun getCareerById(careerID: Int): Flow<Career>

    // Get Career by user id
    @Query("SELECT * FROM careers WHERE career_user_id = :userID")
    fun getUserByUserName(userID: String): Flow<Career>

    // Insert Career
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(career: Career)

    // Update Career
    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(career: Career)

    // Delete Career
    @Query("DELETE FROM careers WHERE career_user_id = :userId")
    suspend fun delete(userId: Int)

    // get All Careers
    @Query("SELECT * FROM careers")
    fun getAllCareers(): Flow<List<Career>>

}
