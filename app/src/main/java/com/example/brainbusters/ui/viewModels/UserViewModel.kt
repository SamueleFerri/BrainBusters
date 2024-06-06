package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.User
import com.example.brainbusters.data.repositories.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UsersState(val users: List<User>, val isLoading: Boolean = false, val errorMessage: String? = null)

interface UsersActions {
    fun addUser(user: User): Job
    fun removeUser(userId: String): Job
    fun updateUser(user: User): Job
}

class UserViewModel(
    private val userRepository: UsersRepository
) : ViewModel() {

    val state = userRepository.users.map { UsersState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UsersState(emptyList())
    )

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val actions = object : UsersActions {
        override fun addUser(user: User) = viewModelScope.launch {
            _isLoading.value = true
            try {
                userRepository.insertNewUser(user)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to add user: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }

        override fun removeUser(userId: String) = viewModelScope.launch {
            _isLoading.value = true
            try {
                userRepository.deleteUserById(userId)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to remove user: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }

        override fun updateUser(user: User) = viewModelScope.launch {
            _isLoading.value = true
            try {
                userRepository.updateUser(user)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update user: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
