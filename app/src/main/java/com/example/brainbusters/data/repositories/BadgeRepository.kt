package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.BadgesDao
import com.example.brainbusters.data.entities.Badge
import kotlinx.coroutines.flow.Flow

class BadgeRepository(private val badgesDAO: BadgesDao) {

    val badges: Flow<List<Badge>> = badgesDAO.getAllbadges()

    suspend fun insertBadge(badge: Badge) {
        badgesDAO.insertBadge(badge)
    }

    fun getAllBages(): Flow<List<Badge>> {
        return badgesDAO.getAllbadges()
    }

    suspend fun getBadgeById(badgeId: Int): Badge?{
        return badgesDAO.getBadgeById(badgeId)
    }
}