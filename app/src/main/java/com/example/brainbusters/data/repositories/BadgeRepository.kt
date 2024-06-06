package com.example.brainbusters.data.repositories

import com.example.brainbusters.data.daos.BadgesDao
import com.example.brainbusters.data.entities.Badge
import kotlinx.coroutines.flow.Flow

class BadgeRepository(private val badgesDAO: BadgesDao) {

    val badges: Flow<List<Badge>> = badgesDAO.getAllbadges()

    suspend fun insertNewBadge(badge: Badge) {
        badgesDAO.insert(badge)
    }

    fun getAllBages() {
        badgesDAO.getAllbadges()
    }

    fun getBadgeById(badgeId: Int) {
        badgesDAO.getBadgeById(badgeId)
    }
}