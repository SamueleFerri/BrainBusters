package com.example.brainbusters.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brainbusters.data.entities.Badge
import kotlinx.coroutines.flow.Flow

@Dao
interface BadgesDao {

    // Insert Badge
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBadge(badge: Badge)


    // Get All Badge
    @Query("SELECT * FROM badges")
    fun getAllbadges(): Flow<List<Badge>>

    // Get Badge by id
    @Query("SELECT * FROM badges WHERE badge_id = :badgeId")
    suspend fun getBadgeById(badgeId: Int): Badge?
}