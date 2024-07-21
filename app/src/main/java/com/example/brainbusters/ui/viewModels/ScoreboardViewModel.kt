package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Career
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.BadgeRepository
import com.example.brainbusters.data.repositories.CareerRepository
import com.example.brainbusters.data.repositories.UsersRepository
import com.example.brainbusters.ui.views.ScoreboardEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class ScoreboardViewModel(
    private val careerRepository: CareerRepository,
    private val userRepository: UsersRepository,
    private val badgeRepository: BadgeRepository
) : ViewModel() {

    private val careersFlow: Flow<List<Career>> = careerRepository.getScoreBoard()
    private val usersFlow: Flow<List<User>> = userRepository.getAllUsers()

    private val userEmail = UserViewModel.getEmail()

    // Combine both flows to create a list of ScoreboardEntry
    val scoreboardEntries: Flow<List<ScoreboardEntry>> = combine(careersFlow, usersFlow) { careers, users ->
        careers.mapNotNull { career ->
            val user = users.find { it.userId == career.userId }
            user?.let { user ->
                val badgeColor = runBlocking {
                    badgeRepository.getBadgeById(career.badgeId)?.color ?: "#808080"
                }
                ScoreboardEntry(
                    position = careers.indexOf(career) + 1,
                    nickname = user.userUsername,
                    quizzesCompleted = career.score,
                    isCurrentUser = user.userId == getCurrentUserId() // Add flag to identify current user
                )
            }
        }.take(9)
    }

    private fun getCurrentUserId(): Int? {
        return runBlocking { userRepository.getUserIdfromMail(userEmail) }
    }

    private val currentUserIdFlow: Flow<Int?> = flow {
        emit(userRepository.getUserIdfromMail(userEmail))
    }.flatMapLatest { userId ->
        // Emit the userId if not null, otherwise emit null
        flowOf(userId)
    }

    // Flow for user at the bottom of the list
    val currentUserScoreboardEntry: Flow<ScoreboardEntry?> = combine(
        careersFlow,
        usersFlow,
        currentUserIdFlow
    ) { careers, users, currentUserId ->
        val currentUserCareer = careers.find { it.userId == currentUserId }
        val currentUser = users.find { it.userId == currentUserId }
        currentUserCareer?.let { career ->
            currentUser?.let { user ->
                ScoreboardEntry(
                    position = careers.indexOf(career) + 1,
                    nickname = user.userUsername,
                    quizzesCompleted = career.score,
                    isCurrentUser = true // Flag for highlighting
                )
            }
        }
    }


}