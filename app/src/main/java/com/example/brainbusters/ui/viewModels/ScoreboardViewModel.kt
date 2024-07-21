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
import com.example.brainbusters.ui.views.ScoreboardEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.koinViewModel

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
        careers.mapNotNull { career ->
            val user = users.find { it.userId == career.userId }
            user?.let { user ->
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
        }.take(9)
    }

    private suspend fun getQuizDoneById(userId: Int): Int {
        return try {
            Log.d("ScoreboardViewModel", "Getting quiz done count for user $userId")
            val quizzes = quizDoneRepository.getQuizzesDoneByUserId(userId).first()
            quizzes.count()
        } catch (e: Exception) {
            Log.e("ScoreboardViewModel", "Error fetching quiz done count", e)
            0
        }
    }

    private suspend fun getUserLevel(userId: Int): Int {
        val career =  careerRepository.getCareerByUserId(userId).first()
        var level = 0
        if (career != null) {
            if(career.score >= 10){
                level = career.score / 10
                if (career.score % 10 >= 5){
                    level -= 1
                }
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