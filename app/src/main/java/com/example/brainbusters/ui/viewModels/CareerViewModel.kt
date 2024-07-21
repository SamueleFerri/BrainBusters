package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Career
import com.example.brainbusters.data.repositories.CareerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CareerViewModel(private val careerRepository: CareerRepository) : ViewModel() {

    private val _careers = MutableStateFlow<List<Career>>(emptyList())
    val careers: StateFlow<List<Career>> get() = _careers

    fun setCareer(career: Career) = viewModelScope.launch {
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
            careerRepository.insertNewCareer(career)
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

    
    fun getUserScore(UserId: Int): Int {
        return 20
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