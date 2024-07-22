package com.example.brainbusters.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Career
import com.example.brainbusters.data.repositories.BadgeRepository
import com.example.brainbusters.data.repositories.CareerRepository
import com.example.brainbusters.data.repositories.QuizDoneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.koinViewModel

class CareerViewModel(
    private val careerRepository: CareerRepository,
    private val quizRepository: QuizDoneRepository,
    private val badgeRepository: BadgeRepository
) : ViewModel() {

    private val _careers = MutableStateFlow<List<Career>>(emptyList())
    val careers: StateFlow<List<Career>> get() = _careers
    fun insertCareer(career: Career) = viewModelScope.launch {
        try {
            careerRepository.insertNewCareer(career)
        } catch (e: Exception) {
            // Gestione degli errori
            throw e
        }
    }

    fun getCareer(userId: Int): Flow<Career?> {
        return careerRepository.getCareerByUserId(userId)
    }

    fun updateCareer(career: Career) = viewModelScope.launch {
        try {
            val quizTaken = getQuizTaken(career.userId)
            val badge = badgeRepository.getAllBages().firstOrNull()
            val badgeId = badge?.filter { it.requiredQuizes <= quizTaken }
                ?.maxByOrNull { it.requiredQuizes }
                ?.badgeId?:1
            val updatedCareer = career.copy(score = getUserScore(career.userId), badgeId = badgeId)
            Log.e("TAG", "user: $updatedCareer")
            careerRepository.updateCareer(updatedCareer)
        } catch (e: Exception) {
            // Gestione degli errori
            throw e
        }
    }

    fun deleteCareer(userId: Int) = viewModelScope.launch {
        try {
            careerRepository.deleteCareerByUserId(userId)
        } catch (e: Exception) {
            // Gestione degli errori
            throw e
        }
    }

    fun getScoreBoard(): Flow<List<Career>> {
        return careerRepository.getScoreBoard()
    }

    fun getQuizTaken(userId: Int): Int {
        return runBlocking {
            try {
                quizRepository.getQuizzesDoneByUserId(userId).firstOrNull()!!.count()
            } catch (e: Exception) {
                Log.e("CareerViewModel", "Error fetching quizzes taken", e)
                0
            }
        }
    }

    fun getUserScore(userId: Int): Int {
        return runBlocking {
            try {
                val quizzesDone = quizRepository.getQuizzesDoneByUserId(userId).firstOrNull() ?: emptyList()
                quizzesDone.sumOf { it.score }
            } catch (e: Exception) {
                Log.e("CareerViewModel", "Error fetching user score", e)
                0
            }
        }
    }

    fun getUserLevel(userId: Int): Int {
        val score = getUserScore(userId)
        Log.e("LOG", "$score")
        var level = 1
        if(score >= 10){
            level = score / 10
            if (score % 10 >= 5){
                level -= 1
            }
        }
        Log.e("LOG", "$level")
        return level
    }

    init {
        loadAllCareers()
    }

    private fun loadAllCareers() = viewModelScope.launch {
        careerRepository.careers.collect { careersList ->
            _careers.value = careersList
        }
    }
}