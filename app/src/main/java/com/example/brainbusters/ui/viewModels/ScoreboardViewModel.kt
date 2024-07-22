package com.example.brainbusters.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Career
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.BadgeRepository
import com.example.brainbusters.data.repositories.CareerRepository
import com.example.brainbusters.data.repositories.QuizDoneRepository
import com.example.brainbusters.data.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.koinViewModel
import kotlin.math.floor

data class ScoreboardEntry(
    val position: Int,
    val nickname: String,
    val score: Int,
    val quizzesCompleted: Int,
    val profileImageUrl: String,
    val level: Int,
    val isCurrentUser: Boolean = false
)

class ScoreboardViewModel(
    private val careerRepository: CareerRepository,
    private val userRepository: UsersRepository,
    private val badgeRepository: BadgeRepository,
    private val quizDoneRepository: QuizDoneRepository
) : ViewModel() {

    private val careersFlow: Flow<List<Career>> = careerRepository.getScoreBoard()
    private val usersFlow: Flow<List<User>> = userRepository.getAllUsers()

    private val userEmail = UserViewModel.getEmail()

    // Combine both flows to create a list of ScoreboardEntry
    val scoreboardEntries: Flow<List<ScoreboardEntry>> = combine(careersFlow, usersFlow) { careers, users ->
        val currentUser = users.find { it.userEmail == userEmail }
        val top9Entries = careers.mapNotNull { career ->
            val user = users.find { it.userId == career.userId }
            user?.let {
                val badgeColor = runBlocking {
                    badgeRepository.getBadgeById(career.badgeId)?.color ?: "#808080"
                }
                ScoreboardEntry(
                    position = careers.indexOf(career) + 1,
                    nickname = user.userUsername,
                    score = career.score,
                    quizzesCompleted = getQuizDoneById(user.userId),
                    profileImageUrl = user.userImage,
                    level = getUserLevel(user.userId),
                    isCurrentUser = user.userId == getCurrentUserId() // Add flag to identify current user
                )
            }
        }.take(9).toMutableList()

        currentUser?.let { user ->
            val currentUserCareer = careers.find { it.userId == user.userId }
            currentUserCareer?.let { career ->
                if (top9Entries.none { it.isCurrentUser }) {
                    top9Entries.add(
                        ScoreboardEntry(
                            position = careers.indexOf(career) + 1,
                            nickname = user.userUsername,
                            score = career.score,
                            quizzesCompleted = getQuizDoneById(user.userId),
                            profileImageUrl = user.userImage,
                            level = getUserLevel(user.userId),
                            isCurrentUser = true
                        )
                    )
                }
            }
        }
        top9Entries
    }

    private suspend fun getQuizDoneById(userId: Int): Int {
        return try {
            val quizzes = quizDoneRepository.getQuizzesDoneByUserId(userId).first()
            quizzes.count()
        } catch (e: Exception) {
            Log.e("ScoreboardViewModel", "Error fetching quiz done count", e)
            0
        }
    }

    private suspend fun getUserLevel(userId: Int): Int {
        val career = careerRepository.getCareerByUserId(userId).first()
        var level = 0
        if (career != null) {
            if (career.score >= 10) {
                level = floor(career.score.toDouble() / 10).toInt()
            }
        }
        return level
    }

    private fun getCurrentUserId(): Int {
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
                    score = career.score,
                    quizzesCompleted = career.score,
                    profileImageUrl = userRepository.getUserById(getCurrentUserId()).first().userImage,
                    level = getUserLevel(user.userId),
                    isCurrentUser = true // Flag for highlighting
                )
            }
        }
    }
}